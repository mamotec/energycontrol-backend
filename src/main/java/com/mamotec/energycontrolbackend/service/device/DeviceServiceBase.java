package com.mamotec.energycontrolbackend.service.device;

import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class DeviceServiceBase {

    private final DeviceRepository deviceRepository;

    public void markDeviceAsActive(Device device, boolean active) {
        deviceRepository.markDeviceAsActive(device.getId(), active);
    }

}
