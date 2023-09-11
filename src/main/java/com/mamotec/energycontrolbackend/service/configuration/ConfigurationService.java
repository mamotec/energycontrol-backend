package com.mamotec.energycontrolbackend.service.configuration;

import com.mamotec.energycontrolbackend.domain.configuration.SystemConfiguration;
import com.mamotec.energycontrolbackend.repository.ConfigurationRepository;
import com.mamotec.energycontrolbackend.service.CrudOperations;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConfigurationService implements CrudOperations<SystemConfiguration> {

    private final ConfigurationRepository repository;

    public SystemConfiguration get() {
        return repository.findById(1L).orElseThrow();
    }

    @Override
    public Optional<JpaRepository<SystemConfiguration, Long>> getRepository() {
        return Optional.of(repository);
    }


}
