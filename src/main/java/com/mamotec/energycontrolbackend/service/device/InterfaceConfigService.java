package com.mamotec.energycontrolbackend.service.device;

import com.mamotec.energycontrolbackend.domain.device.InterfaceConfig;
import com.mamotec.energycontrolbackend.domain.device.dao.InterfaceConfigResponse;
import com.mamotec.energycontrolbackend.mapper.InterfaceConfigMapper;
import com.mamotec.energycontrolbackend.repository.InterfaceConfigRepository;
import com.mamotec.energycontrolbackend.service.CrudOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InterfaceConfigService implements CrudOperations<InterfaceConfig> {

    private final InterfaceConfigRepository repository;

    private final InterfaceConfigMapper mapper;

    @Autowired
    public InterfaceConfigService(InterfaceConfigRepository repository, InterfaceConfigMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public InterfaceConfigResponse create(InterfaceConfig interfaceConfig) {
        return mapper.map(save(interfaceConfig));
    }

    @Override
    public Optional<JpaRepository<InterfaceConfig, Integer>> getRepository() {
        return Optional.of(repository);
    }
}
