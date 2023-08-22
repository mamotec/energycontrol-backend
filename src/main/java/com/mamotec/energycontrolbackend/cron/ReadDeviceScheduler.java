package com.mamotec.energycontrolbackend.cron;

import com.mamotec.energycontrolbackend.client.NodeRedClient;
import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.InterfaceConfig;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.yaml.DeviceYaml;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.yaml.InterfaceYaml;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.yaml.RegisterMapping;
import com.mamotec.energycontrolbackend.service.device.DeviceDataService;
import com.mamotec.energycontrolbackend.service.device.DeviceService;
import com.mamotec.energycontrolbackend.service.interfaceconfig.InterfaceConfigService;
import com.mamotec.energycontrolbackend.service.interfaceconfig.InterfaceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReadDeviceScheduler {

    private final InterfaceConfigService interfaceConfigService;
    private final InterfaceService interfaceService;
    private final DeviceService deviceService;
    private final NodeRedClient nodeRedClient;
    private final DeviceDataService deviceDataService;

    @Scheduled(cron = "*/59 * * * * *")
    @Transactional
    public void fetchDeviceData() throws IOException, InterruptedException {
        List<InterfaceConfig> configs = interfaceConfigService.findAll();
        log.info("READ - Found {} interfaces in repository.", configs.size());

        for (InterfaceConfig config : configs) {

            List<Device> devices = deviceService.getDevicesForInterfaceConfig(config.getId());

            log.info("READ - Found {} devices for interface {}.", devices.size(), config.getType());
            for (Device device : devices) {
                DeviceYaml i = interfaceService.getDeviceInformationForManufactureAndDeviceId(device.getManufacturerId(), device.getDeviceId());

                // Which register mapping to use?
                RegisterMapping mapping = i.getMapping()
                        .getPower();

                doFetchPerDevice(config, device, i, mapping);
            }
        }
    }

    private void doFetchPerDevice(InterfaceConfig config, Device d, DeviceYaml deviceYaml, RegisterMapping mapping) throws IOException, InterruptedException {
        // Fetch data from node-red
        String res = nodeRedClient.fetchDeviceData(deviceYaml, config, d.getUnitId(), mapping);
        // Save data to influxdb
        deviceDataService.writeDeviceData(d, res, mapping);
    }


}
