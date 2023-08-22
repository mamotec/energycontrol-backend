package com.mamotec.energycontrolbackend.service.device;

import com.mamotec.energycontrolbackend.base.SpringBootBaseTest;
import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.device.dao.DeviceCreateResponse;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.InterfaceConfig;
import com.mamotec.energycontrolbackend.factory.DeviceFactory;
import com.mamotec.energycontrolbackend.factory.InterfaceConfigFactory;
import com.mamotec.energycontrolbackend.repository.InterfaceConfigRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DeviceServiceTest extends SpringBootBaseTest {

    @Autowired
    private DeviceService deviceService;
    @Autowired
    private InterfaceConfigRepository interfaceConfigRepository;

    @Nested
    class Create {

        @Test
        void shouldCreateDevice() {
            // given
            InterfaceConfig config = InterfaceConfigFactory.aInterfaceConfig(interfaceConfigRepository);

            // when
            DeviceCreateResponse deviceCreateResponse = deviceService.create(DeviceFactory.aDevice(config));

            // then
            assertNotNull(deviceCreateResponse);
            assertNotNull(deviceCreateResponse.getUnitId());
            assertEquals(1, deviceCreateResponse.getUnitId());
        }

    }

    @Nested
    class GetDevicesForInterfaceConfigYaml {

        @Test
        void shouldReturnDevicesForInterfaceConfig() {
            // given
            InterfaceConfig config = InterfaceConfigFactory.aInterfaceConfig(interfaceConfigRepository);
            deviceService.create(DeviceFactory.aDevice(config));

            // when
            List<Device> devicesForInterfaceConfig = deviceService.getDevicesForInterfaceConfig(config.getId());

            // then
            assertEquals(1, devicesForInterfaceConfig.size());
        }

    }

    @Nested
    class GetAllDevices {

        @Test
        void shouldReturnAllDevices() {
            // given
            InterfaceConfig config = InterfaceConfigFactory.aInterfaceConfig(interfaceConfigRepository);
            deviceService.create(DeviceFactory.aDevice(config));
            deviceService.create(DeviceFactory.aDevice(config));
            deviceService.create(DeviceFactory.aDevice(config));

            // when
            List<Device> allDevices = deviceService.getAllDevices();

            // then
            assertEquals(3, allDevices.size());
        }
    }


}