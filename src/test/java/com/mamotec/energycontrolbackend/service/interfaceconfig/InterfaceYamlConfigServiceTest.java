package com.mamotec.energycontrolbackend.service.interfaceconfig;

import com.mamotec.energycontrolbackend.base.SpringBootBaseTest;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.InterfaceConfig;
import com.mamotec.energycontrolbackend.factory.InterfaceConfigFactory;
import com.mamotec.energycontrolbackend.repository.InterfaceConfigRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class InterfaceYamlConfigServiceTest extends SpringBootBaseTest {

    @Autowired
    private InterfaceConfigService interfaceConfigService;
    @Autowired
    private InterfaceConfigRepository interfaceConfigRepository;

    @Nested
    class Create {

        @Test
        void shouldCreateInterfaceConfig() {
            // when
            InterfaceConfig interfaceConfig = interfaceConfigService.save(InterfaceConfigFactory.aInterfaceConfig());
            // then
            assertNotNull(interfaceConfig);
            assertNotNull(interfaceConfig.getId());
        }
    }

    @Nested
    class FindAll {

        @Test
        void shouldReturnAllInterfaceConfigs() {
            // given
            InterfaceConfigFactory.aInterfaceConfig(interfaceConfigRepository);
            // when
            List<InterfaceConfig> interfaceConfigs = interfaceConfigService.findAll();
            // then
            assertNotNull(interfaceConfigs);
            assertEquals(1, interfaceConfigs.size());
        }
    }

    @Nested
    class Delete {

        @Test
        void shouldDeleteInterfaceConfig() {
            // given
            InterfaceConfig interfaceConfig = InterfaceConfigFactory.aInterfaceConfig(interfaceConfigRepository);
            // when
            interfaceConfigService.delete(interfaceConfig.getId().intValue());
            // then
            assertEquals(0, interfaceConfigRepository.count());
        }
    }

}