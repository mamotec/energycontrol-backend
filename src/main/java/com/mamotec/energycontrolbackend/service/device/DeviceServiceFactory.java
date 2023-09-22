package com.mamotec.energycontrolbackend.service.device;

import com.mamotec.energycontrolbackend.domain.configuration.ApplicationMode;
import com.mamotec.energycontrolbackend.mapper.DeviceMapper;
import com.mamotec.energycontrolbackend.repository.DeviceGroupRepository;
import com.mamotec.energycontrolbackend.repository.DeviceRepository;
import com.mamotec.energycontrolbackend.service.device.home.HomeDeviceService;
import com.mamotec.energycontrolbackend.service.device.plant.PlantDeviceService;
import com.mamotec.energycontrolbackend.service.interfaceconfig.InterfaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeviceServiceFactory {

    private final DeviceRepository deviceRepository;

    private final InterfaceService interfaceService;

    private final DeviceValidationService validationService;

    private final DeviceGroupRepository deviceGroupRepository;

    private final DeviceMapper deviceMapper;

    @Value("${application.mode}")
    private String applicationMode;

    public DeviceService createService() {
        ApplicationMode mode = ApplicationMode.valueOf(applicationMode);

        if (mode.equals(ApplicationMode.HOME)) {
            return new HomeDeviceService(deviceRepository, validationService, deviceGroupRepository, deviceMapper);
        } else {
            return new PlantDeviceService(deviceRepository, interfaceService, validationService, deviceGroupRepository, deviceMapper);
        }
    }
}
