package com.mamotec.energycontrolbackend.cron;

import com.mamotec.energycontrolbackend.client.NodeRedClient;
import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.device.SerialDevice;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.InterfaceConfig;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.yaml.DeviceYaml;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.yaml.RegisterMapping;
import com.mamotec.energycontrolbackend.service.device.DeviceDataService;
import com.mamotec.energycontrolbackend.service.device.plant.PlantDeviceService;
import com.mamotec.energycontrolbackend.service.interfaceconfig.InterfaceConfigService;
import com.mamotec.energycontrolbackend.service.interfaceconfig.InterfaceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class WriteDeviceScheduler {

    private final InterfaceConfigService interfaceConfigService;
    private final InterfaceService interfaceService;
    private final PlantDeviceService deviceService;
    private final NodeRedClient nodeRedClient;
    private final DeviceDataService deviceDataService;

    @Transactional
    public void writeDeviceData() {
        List<InterfaceConfig> configs = interfaceConfigService.findAll();
        log.info("WRITE - Found {} interfaces in repository.", configs.size());

        for (InterfaceConfig config : configs) {

            List<Device> devices = deviceService.getDevicesForInterfaceConfig(config.getId());
            log.info("WRITE - Found {} devices for interface {}.", devices.size(), config.getType());
            for (Device device : devices) {
                SerialDevice serialDevice = (SerialDevice) device;
                DeviceYaml i = interfaceService.getDeviceInformationForManufactureAndDeviceId(serialDevice);

                // Which register mapping to use?
                RegisterMapping mapping = i.getMapping()
                        .getPower();

                deviceDataService.readLastDeviceData(List.of(device.getId()), mapping.getType());
            }
        }
    }

}
