package com.mamotec.energycontrolbackend.service.group;

import com.mamotec.energycontrolbackend.base.SpringBootBaseTest;
import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.device.DeviceType;
import com.mamotec.energycontrolbackend.domain.device.dao.DeviceLinkRequest;
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
    private DeviceGroupService deviceGroupService;

    @Nested
    class PlantDeviceGroup {

        @Nested
        class Create {


            @Test
            void shouldCreateDeviceGroup() {
                // given
                com.mamotec.energycontrolbackend.domain.group.PlantDeviceGroup deviceGroup = DeviceGroupFactory.aPlantDeviceGroup();

                // when
                com.mamotec.energycontrolbackend.domain.group.DeviceGroup save = deviceGroupService.save(deviceGroup);

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
                com.mamotec.energycontrolbackend.domain.group.PlantDeviceGroup deviceGroup = DeviceGroupFactory.aPlantDeviceGroup(deviceGroupRepository);

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
                com.mamotec.energycontrolbackend.domain.group.PlantDeviceGroup deviceGroup = DeviceGroupFactory.aPlantDeviceGroup(deviceGroupRepository);
                InterfaceConfig config = InterfaceConfigFactory.aInterfaceConfig(interfaceConfigRepository);
                Device d1 = DeviceFactory.aTcpDevice(config, deviceRepository);
                DeviceLinkRequest request = new DeviceLinkRequest(List.of(d1.getId()));

                // when
                deviceGroupService.addDevicesToGroup(deviceGroup.getId(), request);

                // then
                com.mamotec.energycontrolbackend.domain.group.PlantDeviceGroup loadedGroup = (com.mamotec.energycontrolbackend.domain.group.PlantDeviceGroup) deviceGroupService.findById(deviceGroup.getId());
                assertEquals(1, loadedGroup.getDevices()
                        .size());
            }

            @Test
            void shouldLinkTwoDevicesToGroup() {
                // given
                com.mamotec.energycontrolbackend.domain.group.PlantDeviceGroup deviceGroup = DeviceGroupFactory.aPlantDeviceGroup(deviceGroupRepository);
                InterfaceConfig config = InterfaceConfigFactory.aInterfaceConfig(interfaceConfigRepository);
                Device d1 = DeviceFactory.aTcpDevice(config, deviceRepository);
                Device d2 = DeviceFactory.aTcpDevice(config, deviceRepository);
                DeviceLinkRequest request = new DeviceLinkRequest(List.of(d1.getId(), d2.getId()));

                // when
                deviceGroupService.addDevicesToGroup(deviceGroup.getId(), request);

                // then
                com.mamotec.energycontrolbackend.domain.group.PlantDeviceGroup loadedGroup = (com.mamotec.energycontrolbackend.domain.group.PlantDeviceGroup) deviceGroupService.findById(deviceGroup.getId());
                assertEquals(2, loadedGroup.getDevices()
                        .size());
            }

            @Test
            void shouldThrowExceptionWhenLinkIsNotValid() {
                // given
                com.mamotec.energycontrolbackend.domain.group.PlantDeviceGroup deviceGroup = DeviceGroupFactory.aPlantDeviceGroup(deviceGroupRepository);
                InterfaceConfig config = InterfaceConfigFactory.aInterfaceConfig(interfaceConfigRepository);
                Device d1 = DeviceFactory.aTcpDevice(config, deviceRepository);
                d1.setDeviceType(DeviceType.BATTERY);
                deviceRepository.save(d1);
                DeviceLinkRequest request = new DeviceLinkRequest(List.of(d1.getId()));

                // when
                // then
                assertThrows(AddDeviceToGroupException.class, () -> deviceGroupService.addDevicesToGroup(deviceGroup.getId(), request));
            }

            @Test
            void shouldUnlinkOneDeviceFromGroup() {
                // given
                com.mamotec.energycontrolbackend.domain.group.PlantDeviceGroup deviceGroup = DeviceGroupFactory.aPlantDeviceGroup(deviceGroupRepository);
                InterfaceConfig config = InterfaceConfigFactory.aInterfaceConfig(interfaceConfigRepository);
                Device d1 = DeviceFactory.aTcpDevice(config, deviceGroup, deviceRepository);
                DeviceLinkRequest request = new DeviceLinkRequest(List.of(d1.getId()));

                // when
                deviceGroupService.deleteDevicesFromGroup(request);

                // then
                com.mamotec.energycontrolbackend.domain.group.PlantDeviceGroup loadedGroup = (com.mamotec.energycontrolbackend.domain.group.PlantDeviceGroup) deviceGroupService.findById(deviceGroup.getId());
                assertEquals(0, loadedGroup.getDevices()
                        .size());
            }

            @Test
            void shouldUnlinkTwoDevicesFromGroup() {
                // given
                com.mamotec.energycontrolbackend.domain.group.PlantDeviceGroup deviceGroup = DeviceGroupFactory.aPlantDeviceGroup(deviceGroupRepository);
                InterfaceConfig config = InterfaceConfigFactory.aInterfaceConfig(interfaceConfigRepository);
                Device d1 = DeviceFactory.aTcpDevice(config, deviceRepository);
                Device d2 = DeviceFactory.aTcpDevice(config, deviceRepository);
                DeviceLinkRequest request = new DeviceLinkRequest(List.of(d1.getId(), d2.getId()));

                deviceGroupService.addDevicesToGroup(deviceGroup.getId(), request);

                // when
                deviceGroupService.deleteDevicesFromGroup(request);

                // then
                com.mamotec.energycontrolbackend.domain.group.PlantDeviceGroup loadedGroup = (com.mamotec.energycontrolbackend.domain.group.PlantDeviceGroup) deviceGroupService.findById(deviceGroup.getId());
                assertEquals(0, loadedGroup.getDevices()
                        .size());

            }
        }

    }



}