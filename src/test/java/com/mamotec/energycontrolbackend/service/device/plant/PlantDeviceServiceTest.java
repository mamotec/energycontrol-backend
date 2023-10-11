package com.mamotec.energycontrolbackend.service.device.plant;

import com.mamotec.energycontrolbackend.base.SpringBootBaseTest;
import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.device.DeviceType;
import com.mamotec.energycontrolbackend.domain.device.EnergyDistributionEvent;
import com.mamotec.energycontrolbackend.domain.device.HybridInverterDevice;
import com.mamotec.energycontrolbackend.domain.device.dao.DeviceTypeResponse;
import com.mamotec.energycontrolbackend.domain.device.dao.EnergyDistributionResponse;
import com.mamotec.energycontrolbackend.domain.device.dao.HybridInverterCreateRequest;
import com.mamotec.energycontrolbackend.domain.group.dao.plant.PlantDeviceGroup;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.InterfaceConfig;
import com.mamotec.energycontrolbackend.factory.DeviceFactory;
import com.mamotec.energycontrolbackend.factory.DeviceGroupFactory;
import com.mamotec.energycontrolbackend.factory.InterfaceConfigFactory;
import com.mamotec.energycontrolbackend.repository.InterfaceConfigRepository;
import com.mamotec.energycontrolbackend.service.device.plant.PlantDeviceService;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

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

            HybridInverterCreateRequest request = new HybridInverterCreateRequest();
            request.setDeviceType(DeviceType.HYBRID_INVERTER);
            request.setName("Test Device");
            request.setHost("host");
            request.setPort("8887");
            request.setInterfaceConfig(config);
            request.setDeviceId(1);
            request.setManufacturerId(1);
            request.setUnitId(1);

            // when
            Device deviceCreateResponse = deviceService.create(request);

            // then
            assertNotNull(deviceCreateResponse);
        }

        @Test
        void shouldThrowExceptionWhenDeviceWithUnitIdIsAlreadyCreated() {
            // given
            InterfaceConfig config = InterfaceConfigFactory.aInterfaceConfig(interfaceConfigRepository);
            HybridInverterDevice device = DeviceFactory.aHybridInverter(config);
            device.setUnitId(1);
            deviceRepository.save(device);
            HybridInverterCreateRequest request = new HybridInverterCreateRequest();
            request.setDeviceType(DeviceType.HYBRID_INVERTER);
            request.setName("Test Device");
            request.setHost("host");
            request.setPort("8887");
            request.setInterfaceConfig(config);
            request.setDeviceId(1);
            request.setManufacturerId(1);
            request.setUnitId(1);

            // when
            // then
            assertThrows(ConstraintViolationException.class, () -> deviceService.create(request));

        }
    }

    @Nested
    class Delete {

        @Test
        void shouldDeleteDevice() {
            // given
            Device device = DeviceFactory.aChargingStation(deviceRepository);

            // when
            deviceService.delete(device.getId());

            // then
            assertFalse(deviceRepository.existsById(device.getId()));
        }

    }

    @Nested
    class GetDevicesForInterfaceConfigYaml {

        @Test
        void shouldReturnDevicesForInterfaceConfig() {
            // given
            InterfaceConfig config = InterfaceConfigFactory.aInterfaceConfig(interfaceConfigRepository);
            deviceService.save(DeviceFactory.aHybridInverter(config));

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
            deviceService.save(DeviceFactory.aHybridInverter(config));
            deviceService.save(DeviceFactory.aHybridInverter(config));
            deviceService.save(DeviceFactory.aHybridInverter(config));

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
            DeviceFactory.aHybridInverter(config, deviceRepository);
            DeviceFactory.aHybridInverter(config, deviceRepository);

            // when
            List<Device> allDevices = deviceService.fetchValidDeviceGroups(group.getId());

            // then
            assertEquals(2, allDevices.size());
        }

        @Test
        void shouldFindByIdWithEventDescription() {
            // given
            InterfaceConfig config = InterfaceConfigFactory.aInterfaceConfig(interfaceConfigRepository);
            Device device = DeviceFactory.aHybridInverter(config, deviceRepository);

            // when
            Device byId = deviceService.findById(device.getId());

            // then
            assertNotNull(byId.getEventDescription());
            assertNotNull(byId.getEventName());
            assertTrue(byId.getEnergyDistributionEvent()
                    .equals(EnergyDistributionEvent.UNMANAGED));
        }
    }

    @Nested
    class GetDeviceTypes {
        @Test
        void getDeviceTypesForPlant() {
            // given
            // when
            List<DeviceTypeResponse> deviceTypes = deviceService.getAllDeviceTypes();

            // then
            assertEquals(4, deviceTypes.size());
        }
    }

    @Nested
    class GetAllEnergyDistributionEvents {
        @Test
        void getEnergyDistributionEvents() {
            // given
            // when
            List<EnergyDistributionResponse> energyDistributionEvents = deviceService.getAllEnergyDistributionEvents(DeviceType.BATTERY);

            // then
            assertEquals(2, energyDistributionEvents.size());
        }
    }


}