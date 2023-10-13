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
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class TcpDeviceDataReader {

    private final InterfaceService interfaceService;
    private final PlantDeviceService deviceService;
    private final DeviceDataService deviceDataService;
    private final ChargingStationService chargingStationService;


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

        // Periode erstellen - hier wird das Limit gesetzt in Ampere
        ChargingSchedulePeriod period = new ChargingSchedulePeriod();
        period.setStartPeriod(0);  // Startzeit des Zeitraums in Sekunden
        period.setLimit(6d);  // Ladeleistungslimit in Ampere


        ChargingSchedule schedule = new ChargingSchedule();
        schedule.setChargingRateUnit(ChargingRateUnitType.A);  // Ladeleistungseinheit setzen
        ChargingSchedulePeriod[] periods = new ChargingSchedulePeriod[1];  // Array für Perioden erstellen
        periods[0] = period;  // Periode dem Array hinzufügen
        schedule.setChargingSchedulePeriod(periods);  // Zeitraum zur Liste hinzufügen

        ChargingProfile profile = new ChargingProfile();
        profile.setChargingProfileId(1);  // ID des Ladeprofils setzen
        profile.setStackLevel(1);  // Stacklevel setzen
        profile.setChargingProfilePurpose(ChargingProfilePurposeType.TxProfile);  // Zweck des Ladeprofils setzen
        profile.setChargingProfileKind(ChargingProfileKindType.Absolute);  // Art des Ladeprofils setzen
        profile.setChargingSchedule(schedule);  // Zeitraum dem Ladeprofil hinzufügen

        SetChargingProfileRequest request = new SetChargingProfileRequest(0, profile);
        SetChargingProfileRequest request1 = new SetChargingProfileRequest(1, profile);

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

        try {
            instance.send(chargingStationDevice.getUuid(), request1).whenComplete((confirmation, throwable) -> {
                if (throwable != null) {
                    log.error("ChargingProfileRequest 1: {}", throwable.getMessage());
                } else {
                    log.info("ChargingProfileRequest 1: {}", confirmation);
                }
            });
        } catch (OccurenceConstraintException | UnsupportedFeatureException | NotConnectedException e) {
            throw new RuntimeException(e);
        }
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
