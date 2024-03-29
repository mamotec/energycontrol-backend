package com.mamotec.energycontrolbackend.reader;

import com.mamotec.energycontrolbackend.client.ModbusTCPClient;
import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.device.HybridInverterDevice;
import com.mamotec.energycontrolbackend.domain.group.dao.home.HomeDataRepresentation;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.InterfaceConfig;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.yaml.DeviceYaml;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.yaml.RegisterMapping;
import com.mamotec.energycontrolbackend.service.device.DeviceDataWriteService;
import com.mamotec.energycontrolbackend.service.device.DeviceServiceBase;
import com.mamotec.energycontrolbackend.service.device.plant.PlantDeviceService;
import com.mamotec.energycontrolbackend.service.group.home.HomeAggregateDeviceGroupDataService;
import com.mamotec.energycontrolbackend.service.interfaceconfig.InterfaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class TcpDeviceDataReaderHome {

    private final InterfaceService interfaceService;
    private final PlantDeviceService deviceService;
    private final DeviceDataWriteService deviceDataWriteService;
    private final DeviceServiceBase deviceServiceBase;
    private final HomeAggregateDeviceGroupDataService homeAggregateDeviceGroupDataService;

    public void fetchDeviceData(InterfaceConfig config) {
        List<Device> devices = deviceService.getDevicesForInterfaceConfig(config.getId());

        log.info("READ - Found {} devices for interface {}.", devices.size(), config.getType());
        for (Device device : devices) {
            if (device instanceof HybridInverterDevice) {
                readHybridInverter(device);
            }
        }
    }

    private void doFetchPerDevice(Device d, RegisterMapping mapping) throws Exception {
        ModbusTCPClient client = new ModbusTCPClient(d.getHost(), Integer.parseInt(d.getPort()));

        String result = client.readHoldingRegisters(mapping.getRegister()
                .get(0), mapping.getRegister()
                .size(), d.getUnitId());

        // Save data to influxdb
        deviceDataWriteService.writeDeviceData(d, String.valueOf(result), mapping.getType());
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

        RegisterMapping genPower = i.getMapping()
                .getGenPower();

        try {
            doFetchPerDevice(device, genPower);
            doFetchPerDevice(device, inverterPower);
            doFetchPerDevice(device, batterySoc);
            doFetchPerDevice(device, batteryPower);
            doFetchPerDevice(device, gridPower);
            writeHouseHoldPower(device);
        } catch (Exception e) {
            noError = false;
            log.error("READ - Error while fetching data for device {}.", device.getId(), e);
        }
        deviceServiceBase.markDeviceAsActive(device, noError);
    }

    private void writeHouseHoldPower(Device device) {
        HomeDataRepresentation aggregate = homeAggregateDeviceGroupDataService.aggregate();

        deviceDataWriteService.writeDeviceData(device, String.valueOf(aggregate.getHouseHoldPower()), "houseHoldPower");
    }
}
