package com.mamotec.energycontrolbackend.service.group;

import com.mamotec.energycontrolbackend.base.SpringBootBaseTest;
import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.device.DeviceType;
import com.mamotec.energycontrolbackend.domain.group.DeviceGroup;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.InterfaceConfig;
import com.mamotec.energycontrolbackend.exception.AddDeviceToGroupException;
import com.mamotec.energycontrolbackend.factory.DeviceFactory;
import com.mamotec.energycontrolbackend.factory.DeviceGroupFactory;
import com.mamotec.energycontrolbackend.factory.InterfaceConfigFactory;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DeviceGroupServiceTest extends SpringBootBaseTest {

    @Autowired
    public DeviceGroupService deviceGroupService;

    @Nested
    class Create {
        @Test
        void shouldCreateDeviceGroup() {
            // given
            DeviceGroup deviceGroup = DeviceGroupFactory.aDeviceGroup();

            // when
            DeviceGroup save = deviceGroupService.save(deviceGroup);

            // then
            assertNotNull(save);
            assertNotNull(save.getId());
            assertEquals("PV - Rechte Seiete", save.getName());
        }
    }

    @Nested
    class Delete {

        @Test
        void shouldDeleteDeviceGroup() {
            // given
            DeviceGroup deviceGroup = DeviceGroupFactory.aDeviceGroup(deviceGroupRepository);

            // when
            deviceGroupService.deleteGroup(deviceGroup.getId());

            // then
            assertEquals(0, deviceGroupRepository.count());
        }
    }

    @Nested
    class Link {
        @Test
        void shouldLinkOneDeviceToGroup() {
            // given
            DeviceGroup deviceGroup = DeviceGroupFactory.aDeviceGroup(deviceGroupRepository);
            InterfaceConfig config = InterfaceConfigFactory.aInterfaceConfig(interfaceConfigRepository);
            Device d1 = DeviceFactory.aDevice(config, 1, deviceRepository);

            // when
            deviceGroupService.addDevicesToGroup(deviceGroup.getId(), List.of(d1));

            // then
            DeviceGroup loadedGroup = deviceGroupService.findById(deviceGroup.getId());
            assertEquals(1, loadedGroup.getDevices()
                    .size());
        }

        @Test
        void shouldLinkTwoDevicesToGroup() {
            // given
            DeviceGroup deviceGroup = DeviceGroupFactory.aDeviceGroup(deviceGroupRepository);
            InterfaceConfig config = InterfaceConfigFactory.aInterfaceConfig(interfaceConfigRepository);
            Device d1 = DeviceFactory.aDevice(config, 1, deviceRepository);
            Device d2 = DeviceFactory.aDevice(config, 2, deviceRepository);

            // when
            deviceGroupService.addDevicesToGroup(deviceGroup.getId(), List.of(d1, d2));

            // then
            DeviceGroup loadedGroup = deviceGroupService.findById(deviceGroup.getId());
            assertEquals(2, loadedGroup.getDevices()
                    .size());
        }

        @Test
        void shouldThrowExceptionWhenLinkIsNotValid() {
            // given
            DeviceGroup deviceGroup = DeviceGroupFactory.aDeviceGroup(deviceGroupRepository);
            InterfaceConfig config = InterfaceConfigFactory.aInterfaceConfig(interfaceConfigRepository);
            Device d1 = DeviceFactory.aDevice(config, 1, deviceRepository);
            d1.setDeviceType(DeviceType.BATTERY);
            deviceRepository.save(d1);

            // when
            // then
            assertThrows(AddDeviceToGroupException.class, () -> deviceGroupService.addDevicesToGroup(deviceGroup.getId(), List.of(d1)));
        }

        @Test
        void shouldUnlinkOneDeviceFromGroup() {
            // given
            DeviceGroup deviceGroup = DeviceGroupFactory.aDeviceGroup(deviceGroupRepository);
            InterfaceConfig config = InterfaceConfigFactory.aInterfaceConfig(interfaceConfigRepository);
            Device d1 = DeviceFactory.aDevice(config, deviceGroup, 1, deviceRepository);

            // when
            deviceGroupService.deleteDevicesFromGroup(List.of(d1));

            // then
            DeviceGroup loadedGroup = deviceGroupService.findById(deviceGroup.getId());
            assertEquals(0, loadedGroup.getDevices()
                    .size());
        }

        @Test
        void shouldUnlinkTwoDevicesFromGroup() {
            // given
            DeviceGroup deviceGroup = DeviceGroupFactory.aDeviceGroup(deviceGroupRepository);
            InterfaceConfig config = InterfaceConfigFactory.aInterfaceConfig(interfaceConfigRepository);
            Device d1 = DeviceFactory.aDevice(config, 1, deviceRepository);
            Device d2 = DeviceFactory.aDevice(config, 2, deviceRepository);
            deviceGroupService.addDevicesToGroup(deviceGroup.getId(), List.of(d1, d2));

            // when
            deviceGroupService.deleteDevicesFromGroup(List.of(d1, d2));

            // then
            DeviceGroup loadedGroup = deviceGroupService.findById(deviceGroup.getId());
            assertEquals(0, loadedGroup.getDevices()
                    .size());

        }
    }

}