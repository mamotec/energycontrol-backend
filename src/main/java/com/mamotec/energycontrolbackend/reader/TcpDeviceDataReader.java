package com.mamotec.energycontrolbackend.reader;

import com.mamotec.energycontrolbackend.client.ModbusTCPClient;
import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.device.HybridInverterDevice;
import com.mamotec.energycontrolbackend.domain.device.chargingstation.ChargingStationDevice;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.InterfaceConfig;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.yaml.DeviceYaml;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.yaml.RegisterMapping;
import com.mamotec.energycontrolbackend.ocpp.OcppServer;
import com.mamotec.energycontrolbackend.service.device.ChargingStationService;
import com.mamotec.energycontrolbackend.service.device.DeviceDataService;
import com.mamotec.energycontrolbackend.service.device.plant.PlantDeviceService;
import com.mamotec.energycontrolbackend.service.interfaceconfig.InterfaceService;
import eu.chargetime.ocpp.JSONServer;
import eu.chargetime.ocpp.NotConnectedException;
import eu.chargetime.ocpp.OccurenceConstraintException;
import eu.chargetime.ocpp.UnsupportedFeatureException;
import eu.chargetime.ocpp.model.core.*;
import eu.chargetime.ocpp.model.smartcharging.SetChargingProfileRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class TcpDeviceDataReader {

    private final InterfaceService interfaceService;
    private final PlantDeviceService deviceService;
    private final DeviceDataService deviceDataService;
    private final ChargingStationService chargingStationService;

    @Value("${chargingstation.unit}")
    private String unit;

    @Value("${chargingstation.value}")
    private Double value;


    public void fetchDeviceData(InterfaceConfig config) {
        List<Device> devices = deviceService.getDevicesForInterfaceConfig(config.getId());

        log.info("READ - Found {} devices for interface {}.", devices.size(), config.getType());
        for (Device device : devices) {
            if (device instanceof HybridInverterDevice) {
                readHybridInverter(device);
            } else if (device instanceof ChargingStationDevice) {
                readChargingStation(device);
            } else {
                log.error("READ - Device {} is not supported.", device.getId());
            }

        }
    }

    private void doFetchPerDevice(Device d, RegisterMapping mapping) throws Exception {
        ModbusTCPClient client = new ModbusTCPClient(d.getHost(), Integer.parseInt(d.getPort()));

        String result = client.readHoldingRegisters(mapping.getRegister()
                .get(0), mapping.getRegister()
                .size(), d.getUnitId());

        // Save data to influxdb
        deviceDataService.writeDeviceData(d, String.valueOf(result), mapping);
    }

    private void readChargingStation(Device device) {
        ChargingStationDevice chargingStationDevice = (ChargingStationDevice) device;

        // Erstelle ein ChargingProfile
        ChargingProfile chargingProfile = new ChargingProfile();
        chargingProfile.setChargingProfileId(1); // Eindeutige ID für das Profil
        chargingProfile.setStackLevel(1); // Prioritätsstufe des Profils
        chargingProfile.setChargingProfilePurpose(ChargingProfilePurposeType.ChargePointMaxProfile);
        chargingProfile.setChargingProfileKind(ChargingProfileKindType.Absolute);

        // Erstelle ein ChargingSchedule
        ChargingSchedule chargingSchedule = new ChargingSchedule();
        chargingSchedule.setDuration(0); // Keine Begrenzung der Dauer
        chargingSchedule.setStartSchedule(ZonedDateTime.now()); // Startzeitpunkt
        chargingSchedule.setChargingRateUnit(unit.equals("W") ? ChargingRateUnitType.W : ChargingRateUnitType.A); // Ladeleistungseinheit

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

        if (request.validate()) {
            log.info("ChargingProfileRequest is valid");
        } else {
            log.error("ChargingProfileRequest is not valid");
        }

        JSONServer instance = OcppServer.getInstance(chargingStationService);
        try {
            instance.send(chargingStationDevice.getUuid(), request).whenComplete((confirmation, throwable) -> {
                if (throwable != null) {
                    log.error("ChargingProfileRequest 0: {}", throwable.getMessage());
                } else {
                    log.info("ChargingProfileRequest 0: {}", confirmation);
                }
            });
        } catch (OccurenceConstraintException | UnsupportedFeatureException | NotConnectedException e) {
            log.error("ChargingProfileRequest: {}", e);
        }

        chargingStationService.setConfiguration(chargingStationDevice.getUuid());
    }

    private void readHybridInverter(Device device) {
        boolean noError = true;
        DeviceYaml i = interfaceService.getDeviceInformationForManufactureAndDeviceId(device);

        // Which register mapping to use?
        RegisterMapping inverterPower = i.getMapping()
                .getPower();

        RegisterMapping batterySoc = i.getMapping()
                .getBatterySoc();

        RegisterMapping batteryPower = i.getMapping()
                .getBatteryPower();

        RegisterMapping gridPower = i.getMapping()
                .getGridPower();

        try {
            doFetchPerDevice(device, inverterPower);
            doFetchPerDevice(device, batterySoc);
            doFetchPerDevice(device, batteryPower);
            doFetchPerDevice(device, gridPower);
        } catch (Exception e) {
            noError = false;
            log.error("READ - Error while fetching data for device {}.", device.getId(), e);
        }
        deviceDataService.markDeviceAsActive(device, noError);
    }
}
