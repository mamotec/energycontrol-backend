package com.mamotec.energycontrolbackend.service.device;


import com.mamotec.energycontrolbackend.domain.device.SerialDevice;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.InterfaceConfig;
import com.mamotec.energycontrolbackend.repository.SerialDeviceRepository;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SerialDeviceValidationService {

    private final SerialDeviceRepository repository;

    public void validate(SerialDevice device) {
        if (existsByUnitIdAndInterfaceConfig(device.getUnitId(), device.getInterfaceConfig())) {
            throw new ConstraintViolationException("Gerät existiert bereits mit dieser Slave-Id für die Konfiguration.", null);
        }
    }

    private boolean existsByUnitIdAndInterfaceConfig(Integer unitId, InterfaceConfig interfaceConfig) {
        return repository.existsByUnitIdAndInterfaceConfig(unitId, interfaceConfig);
    }

}