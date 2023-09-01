package com.mamotec.energycontrolbackend.service.interfaceconfig;

import com.mamotec.energycontrolbackend.domain.interfaceconfig.InterfaceConfig;
import com.mamotec.energycontrolbackend.repository.InterfaceConfigRepository;
import com.mamotec.energycontrolbackend.service.CrudOperations;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InterfaceConfigService implements CrudOperations<InterfaceConfig> {

    private final InterfaceConfigRepository repository;

    @Override
    public Optional<JpaRepository<InterfaceConfig, Long>> getRepository() {
        return Optional.of(repository);
    }

    public List<InterfaceConfig> findAll() {
        return repository.findAll();
    }


}
