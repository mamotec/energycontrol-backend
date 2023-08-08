package com.mamotec.energycontrolbackend.cron;

import com.mamotec.energycontrolbackend.client.DeviceRequestBodyBuilder;
import com.mamotec.energycontrolbackend.client.NodeRedClient;
import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.InterfaceConfig;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.dao.Interface;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.dao.RegisterMapping;
import com.mamotec.energycontrolbackend.service.device.DeviceDataService;
import com.mamotec.energycontrolbackend.service.device.DeviceService;
import com.mamotec.energycontrolbackend.service.interfaceconfig.InterfaceConfigService;
import com.mamotec.energycontrolbackend.service.interfaceconfig.InterfaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeviceScheduler {

    private final InterfaceConfigService interfaceConfigService;
    private final InterfaceService interfaceService;
    private final DeviceService deviceService;
    private final NodeRedClient nodeRedClient;
    private final DeviceDataService deviceDataService;


    @Scheduled(cron = "*/5 * * * * *")
    public void fetchDeviceData() throws IOException, InterruptedException {
        List<InterfaceConfig> configs = interfaceConfigService.findAll();
        log.info("Found {} interfaces in repository.", configs.size());

        for (InterfaceConfig config : configs) {
            Interface i = interfaceService.getInterfaceByProtocolId(config.getProtocolID());

            List<Device> devices = deviceService.getDevicesForInterfaceConfig(config.getId());
            log.info("Found {} devices for interface {}.", devices.size(), config.getType());
            for (Device d : devices) {
                RegisterMapping mapping = i.getMapping().getPower();
                String res = nodeRedClient.fetchDeviceData(i, config, d, map -> DeviceRequestBodyBuilder.buildPostWithMapping(mapping));
                deviceDataService.saveDeviceData(d, mapping, res);
            }
        }

    }
}
