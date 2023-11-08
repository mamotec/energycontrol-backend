package com.mamotec.energycontrolbackend.service.device.chargingStation;

import com.mamotec.energycontrolbackend.domain.device.chargingstation.ChargingStationDevice;
import com.mamotec.energycontrolbackend.domain.group.dao.home.HomeDataRepresentation;
import com.mamotec.energycontrolbackend.ocpp.OcppServer;
import com.mamotec.energycontrolbackend.repository.ChargingStationRepository;
import com.mamotec.energycontrolbackend.service.group.home.HomeAggregateDeviceGroupDataService;
import com.mamotec.energycontrolbackend.utils.ConversionUtils;
import eu.chargetime.ocpp.JSONServer;
import eu.chargetime.ocpp.model.core.*;
import eu.chargetime.ocpp.model.smartcharging.SetChargingProfileRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.mamotec.energycontrolbackend.utils.ConversionUtils.convertWattsToAmps;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChargingStationEnergyDistributionService {

    private final ChargingStationService chargingStationService;
    private final ChargingStationRepository repository;
    private final HomeAggregateDeviceGroupDataService homeAggregateDeviceGroupDataService;

    public void unmanagedEnergyDistribution(ChargingStationDevice device) {
        if (device.isTransactionActive()) {
            SetChargingProfileRequest req = createChargingProfileRequest(16d, device);

            try {
                log.info("Sending SetChargingProfileRequest {} to {}", req, device.getUuid());
                getServer().send(device.getUuid(), req);
            } catch (Exception e) {
                log.error("Error while sending SetChargingProfileRequest", e);
            }
        }

    }

    public void renewableEnergyDistribution(ChargingStationDevice device, boolean higherPriorityThanBattery) {
        Double energyOverflow = getEnergyOverflow(higherPriorityThanBattery);
        if (!device.isTransactionActive()) {
            // Create a new transaction to start loading
            device.setTransactionId(device.getTransactionId() + 1);
            device.setTransactionActive(true);
            repository.save(device);

            SetChargingProfileRequest req = createChargingProfileRequest(energyOverflow, device);

            sendRequest(device, req);
        } else {
            // When transaction is already ongoing then just update the charging profile
            SetChargingProfileRequest req = createChargingProfileRequest(energyOverflow, device);
            try {
                log.info("Sending RemoteStartTransactionRequest {} to {}", req, device.getUuid());
                getServer().send(device.getUuid(), req);
            } catch (Exception e) {
                log.error("Error while sending RemoteStartTransactionRequest", e);
            }
        }
    }

    public void managedEnergyDistribution(ChargingStationDevice device) {
        if (device.isTransactionActive()) {
            SetChargingProfileRequest req = createChargingProfileRequest((double) device.getManagedStrength(), device);

            try {
                log.info("Sending SetChargingProfileRequest {} to {}", req, device.getUuid());
                getServer().send(device.getUuid(), req);
            } catch (Exception e) {
                log.error("Error while sending SetChargingProfileRequest", e);
            }
        }

    }

    private Double getEnergyOverflow(boolean higherPriorityThanBattery) {
        HomeDataRepresentation aggregate = homeAggregateDeviceGroupDataService.aggregate();
        if (higherPriorityThanBattery) {
            Double overflow = (double) -(aggregate.getGrid()
                    .getValue() + aggregate.getBatteryPower()
                    .getValue());
            log.info("HigherPriorityThanBattery: {}, Overflow: {}", true, overflow);
            Double v = convertWattsToAmps(overflow.longValue(), 230L);
            log.info("HigherPriorityThanBattery: {}, Overflow (A): {}", true, v);
            return v;

        } else {
            Double overflow = (double) -(aggregate.getGrid()
                    .getValue());
            log.info("HigherPriorityThanBattery: {}, Overflow: {}", false, overflow);
            Double v = convertWattsToAmps(overflow.longValue(), 230L);
            log.info("HigherPriorityThanBattery: {}, Overflow (A): {}", true, v);
            return v;
        }
    }

    private JSONServer getServer() {
        return OcppServer.getInstance(chargingStationService);
    }

    private void sendRequest(ChargingStationDevice device, SetChargingProfileRequest req) {
        RemoteStartTransactionRequest request = new RemoteStartTransactionRequest();
        request.setConnectorId(1);
        request.setChargingProfile(req.getCsChargingProfiles());
        request.setIdTag("voltpilot");

        try {
            log.info("Sending RemoteStartTransactionRequest {} to {}", request, device.getUuid());
            getServer().send(device.getUuid(), request);
        } catch (Exception e) {
            log.error("Error while sending RemoteStartTransactionRequest", e);
        }
    }

    private SetChargingProfileRequest createChargingProfileRequest(Double value, ChargingStationDevice device) {
        ChargingProfile chargingProfile = new ChargingProfile();
        chargingProfile.setTransactionId(device.getTransactionId() == 0 ? null : device.getTransactionId());
        chargingProfile.setChargingProfileId(1); // Eindeutige ID für das Profil
        chargingProfile.setStackLevel(0); // Prioritätsstufe des Profils
        chargingProfile.setChargingProfilePurpose(ChargingProfilePurposeType.TxProfile);
        chargingProfile.setChargingProfileKind(ChargingProfileKindType.Absolute);
        chargingProfile.setRecurrencyKind(null);

        // Erstelle ein ChargingSchedule
        ChargingSchedule chargingSchedule = new ChargingSchedule();
        chargingSchedule.setChargingRateUnit(ChargingRateUnitType.A); // Ladeleistungseinheit

        // Erstelle eine ChargingSchedulePeriod mit der Begrenzung der Ladeleistung
        ChargingSchedulePeriod chargingSchedulePeriod = new ChargingSchedulePeriod();
        chargingSchedulePeriod.setStartPeriod(0); // Startperiode
        chargingSchedulePeriod.setLimit(value); // Maximaler Ladewert in Watt (10 kW)

        // Füge die ChargingSchedulePeriod zur ChargingSchedule hinzu
        ChargingSchedulePeriod[] strings = new ChargingSchedulePeriod[1];
        strings[0] = chargingSchedulePeriod;
        chargingSchedule.setChargingSchedulePeriod(strings);

        // Setze die ChargingSchedule im ChargingProfile
        chargingProfile.setChargingSchedule(chargingSchedule);

        // Setze das ChargingProfile im SetChargingProfileRequest
        SetChargingProfileRequest request = new SetChargingProfileRequest();
        request.setConnectorId(0);
        request.setCsChargingProfiles(chargingProfile);

        return request;
    }

}
