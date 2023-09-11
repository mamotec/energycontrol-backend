package com.mamotec.energycontrolbackend.service.configuration;

import com.mamotec.energycontrolbackend.domain.configuration.Configuration;
import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.device.dao.DeviceLinkRequest;
import com.mamotec.energycontrolbackend.domain.group.DeviceGroup;
import com.mamotec.energycontrolbackend.domain.group.dao.DeviceGroupCreate;
import com.mamotec.energycontrolbackend.repository.ConfigurationRepository;
import com.mamotec.energycontrolbackend.repository.DeviceGroupRepository;
import com.mamotec.energycontrolbackend.repository.DeviceRepository;
import com.mamotec.energycontrolbackend.service.CrudOperations;
import com.mamotec.energycontrolbackend.service.interfaceconfig.InterfaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.mamotec.energycontrolbackend.service.group.DeviceGroupValidator.validate;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConfigurationService implements CrudOperations<Configuration> {

    private final ConfigurationRepository repository;

    public Configuration get() {
        return repository.findById(1L).orElseThrow();
    }

    @Override
    public Optional<JpaRepository<Configuration, Long>> getRepository() {
        return Optional.of(repository);
    }


}
