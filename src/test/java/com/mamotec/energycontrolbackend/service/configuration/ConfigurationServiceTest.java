package com.mamotec.energycontrolbackend.service.configuration;

import com.mamotec.energycontrolbackend.base.SpringBootBaseTest;
import com.mamotec.energycontrolbackend.domain.configuration.SystemConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConfigurationServiceTest extends SpringBootBaseTest {

    @Autowired
    private ConfigurationService configurationService;

    @Test
    void shouldGetSystemConfiguration() {
        // when
        SystemConfiguration systemConfiguration = configurationService.get();
        // then
        assertNotNull(systemConfiguration);
    }

    @Test
    void shouldGetRepository() {
        // when
        Optional<JpaRepository<SystemConfiguration, Long>> systemConfiguration = configurationService.getRepository();
        // then
        assertTrue(systemConfiguration.isPresent());
    }

}