package com.mamotec.energycontrolbackend.service.device;


import com.mamotec.energycontrolbackend.domain.configuration.ApplicationMode;
import com.mamotec.energycontrolbackend.domain.configuration.ConfigurationHolder;
import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.InterfaceConfig;
import com.mamotec.energycontrolbackend.repository.DeviceRepository;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeviceValidationService {

    private final DeviceRepository repository;

    private final ConfigurationHolder holder;

    public void validate(Device device) {
        if (existsByUnitIdAndInterfaceConfig(device.getUnitId(), device.getInterfaceConfig()) && device.getUnitId() > 0) {
            throw new ConstraintViolationException("Gerät existiert bereits mit dieser Slave-Id für die Konfiguration.", null);
        }

        validateForMode(device);
    }

    private void validateForMode(Device device) {
        if (holder.getApplicationMode().equals(ApplicationMode.HOME)) {
            List<Device> devices = repository.findAll();
            boolean deviceAvailable = devices.stream()
                    .anyMatch(d -> d.getDeviceType()
                            .equals(device.getDeviceType()));
            if (deviceAvailable) {
                throw new ConstraintViolationException("Es existiert bereits ein Gerät dieses Typs.", null);
            }

        }
    }

    private boolean existsByUnitIdAndInterfaceConfig(Integer unitId, InterfaceConfig interfaceConfig) {
        return repository.existsByUnitIdAndInterfaceConfig(unitId, interfaceConfig);
    }

}
