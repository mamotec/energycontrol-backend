package com.mamotec.energycontrolbackend.service.device;


import com.mamotec.energycontrolbackend.domain.configuration.ApplicationMode;
import com.mamotec.energycontrolbackend.domain.configuration.ConfigurationHolder;
import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.device.SerialDevice;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.InterfaceConfig;
import com.mamotec.energycontrolbackend.repository.DeviceRepository;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeviceValidationService {

    private final DeviceRepository repository;

    private final ConfigurationHolder holder;

    public void validate(Device device) {
        if (existsByUnitIdAndInterfaceConfig(device.getUnitId(), device.getInterfaceConfig())) {
            throw new ConstraintViolationException("Gerät existiert bereits mit dieser Slave-Id für die Konfiguration.", null);
        }

        validateForMode(device);
    }

    private void validateForMode(Device device) {
        if (holder.getApplicationMode().equals(ApplicationMode.HOME)) {
            if (repository.existsByDeviceType(device.getDeviceType())) {
                throw new ConstraintViolationException("Es existiert bereits ein Gerät dieses Typs.", null);
            }

        }
    }

    private boolean existsByUnitIdAndInterfaceConfig(Integer unitId, InterfaceConfig interfaceConfig) {
        return repository.existsByUnitIdAndInterfaceConfig(unitId, interfaceConfig);
    }

}
