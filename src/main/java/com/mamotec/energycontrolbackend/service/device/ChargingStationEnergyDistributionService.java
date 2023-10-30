package com.mamotec.energycontrolbackend.service.device;

import com.mamotec.energycontrolbackend.domain.device.chargingstation.ChargingStationDevice;
import com.mamotec.energycontrolbackend.ocpp.OcppServer;
import eu.chargetime.ocpp.JSONServer;
import eu.chargetime.ocpp.model.core.*;
import eu.chargetime.ocpp.model.smartcharging.SetChargingProfileRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChargingStationEnergyDistributionService {

    private final ChargingStationService chargingStationService;

    public void unmanagedEnergyDistribution(ChargingStationDevice device) {
        SetChargingProfileRequest req = createChargingProfileRequest(16d);

        try {
            log.info("Sending SetChargingProfileRequest {} to {}", req, device.getUuid());
            getServer().send(device.getUuid(), req);
        } catch (Exception e) {
            log.error("Error while sending SetChargingProfileRequest", e);
        }
    }

    public void renewableEnergyDistribution(ChargingStationDevice device) {
        SetChargingProfileRequest req = createChargingProfileRequest(6d);

        sendRequest(device, req);
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

    private SetChargingProfileRequest createChargingProfileRequest(Double value) {
        ChargingProfile chargingProfile = new ChargingProfile();
        chargingProfile.setChargingProfileId(1); // Eindeutige ID für das Profil
        chargingProfile.setStackLevel(0); // Prioritätsstufe des Profils
        chargingProfile.setChargingProfilePurpose(ChargingProfilePurposeType.ChargePointMaxProfile);
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
