package com.mamotec.energycontrolbackend.service.device;

import com.mamotec.energycontrolbackend.base.SpringBootBaseTest;
import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.device.DeviceType;
import com.mamotec.energycontrolbackend.domain.group.dao.plant.PlantDeviceGroup;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.InterfaceConfig;
import com.mamotec.energycontrolbackend.factory.DeviceFactory;
import com.mamotec.energycontrolbackend.factory.DeviceGroupFactory;
import com.mamotec.energycontrolbackend.factory.InterfaceConfigFactory;
import com.mamotec.energycontrolbackend.repository.InterfaceConfigRepository;
import com.mamotec.energycontrolbackend.service.device.plant.PlantDeviceService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PlantDeviceServiceTest extends SpringBootBaseTest {

    @Autowired
    private PlantDeviceService deviceService;
    @Autowired
    private InterfaceConfigRepository interfaceConfigRepository;

    @Nested
    class Create {

        @Test
        void shouldCreateDevice() {
            // given
            InterfaceConfig config = InterfaceConfigFactory.aInterfaceConfig(interfaceConfigRepository);

            // when
            Device deviceCreateResponse = deviceService.save(DeviceFactory.aTcpDevice(config));

            // then
            assertNotNull(deviceCreateResponse);
        }

    }

    @Nested
    class GetDevicesForInterfaceConfigYaml {

        @Test
        void shouldReturnDevicesForInterfaceConfig() {
            // given
            InterfaceConfig config = InterfaceConfigFactory.aInterfaceConfig(interfaceConfigRepository);
            deviceService.save(DeviceFactory.aTcpDevice(config));

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
            deviceService.save(DeviceFactory.aTcpDevice(config));
            deviceService.save(DeviceFactory.aTcpDevice(config));
            deviceService.save(DeviceFactory.aTcpDevice(config));

            // when
            List<Device> allDevices = deviceService.getAllDevices();

            // then
            assertEquals(3, allDevices.size());
        }

        @Test
        void shouldReturnValidDevicesForGroup() {
            // given
            InterfaceConfig config = InterfaceConfigFactory.aInterfaceConfig(interfaceConfigRepository);
            PlantDeviceGroup group = DeviceGroupFactory.aPlantDeviceGroup(deviceGroupRepository);
            // Inverter
            DeviceFactory.aTcpDevice(config, deviceRepository);
            DeviceFactory.aTcpDevice(config, deviceRepository);
            // Battery
            Device battery = DeviceFactory.aTcpDevice(config, deviceRepository);
            battery.setDeviceType(DeviceType.BATTERY);
            deviceRepository.save(battery);

            // when
            List<Device> allDevices = deviceService.getValidDevicesForGroup(group.getId());

            // then
            assertEquals(2, allDevices.size());
        }
    }


}