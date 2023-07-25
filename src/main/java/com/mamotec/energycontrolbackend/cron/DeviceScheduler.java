package com.mamotec.energycontrolbackend.cron;

import com.mamotec.energycontrolbackend.client.NodeRedClient;
import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeviceScheduler {

    private final DeviceRepository deviceRepository;

    private final NodeRedClient nodeRedClient;

    @Scheduled(cron = "*/30 * * * * *")
    public void fetchDeviceData() {
        List<Device> devices = deviceRepository.findAll();
        log.info("Found {} devices in repository.", devices.size());

        for (Device device : devices) {
            Object data = nodeRedClient.fetchDeviceData(device.getId());
        }

        // TODO: Write device Data to Repository


    }
}
