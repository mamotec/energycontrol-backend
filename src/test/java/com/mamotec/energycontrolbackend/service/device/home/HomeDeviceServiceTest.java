package com.mamotec.energycontrolbackend.service.device.home;

import com.mamotec.energycontrolbackend.base.SpringBootBaseTest;
import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.device.DeviceType;
import com.mamotec.energycontrolbackend.domain.device.EnergyDistributionEvent;
import com.mamotec.energycontrolbackend.domain.device.HybridInverterDevice;
import com.mamotec.energycontrolbackend.domain.device.dao.DeviceTypeResponse;
import com.mamotec.energycontrolbackend.domain.device.dao.DeviceUpdateRequest;
import com.mamotec.energycontrolbackend.domain.device.dao.EnergyDistributionResponse;
import com.mamotec.energycontrolbackend.domain.device.dao.HybridInverterCreateRequest;
import com.mamotec.energycontrolbackend.domain.group.DeviceGroup;
import com.mamotec.energycontrolbackend.domain.group.dao.home.HomeDeviceGroup;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.InterfaceConfig;
import com.mamotec.energycontrolbackend.factory.DeviceFactory;
import com.mamotec.energycontrolbackend.factory.DeviceGroupFactory;
import com.mamotec.energycontrolbackend.factory.InterfaceConfigFactory;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

class HomeDeviceServiceTest extends SpringBootBaseTest {

    @Autowired
    private HomeDeviceService deviceService;


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
            List<DeviceGroup> groups = deviceGroupRepository.findAll();
            assertNotNull(deviceCreateResponse);
            assertEquals(1, groups.size());
            assertEquals(HomeDeviceGroup.class, groups.get(0)
                    .getClass());

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
    class Update {
        @Test
        void updateEnergyDistribution() {
            // given
            Device device = DeviceFactory.aChargingStation(deviceRepository);

            DeviceUpdateRequest request = new DeviceUpdateRequest();
            request.setEnergyDistributionEvent(EnergyDistributionEvent.RENEWABLE_ENERGY);
            request.setDeviceType(device.getDeviceType());
            request.setPriority(device.getPriority());
            request.setName(device.getName());

            // when
            Device updatedDevice = deviceService.update(device.getId(), request);

            // then
            assertEquals(EnergyDistributionEvent.RENEWABLE_ENERGY, updatedDevice.getEnergyDistributionEvent());
        }

        @Test
        void updatePriority() {
            // given
            Device device = DeviceFactory.aChargingStation(deviceRepository);
            Device device1 = DeviceFactory.aChargingStation();
            device1.setPriority(1);
            deviceRepository.save(device1);

            DeviceUpdateRequest request = new DeviceUpdateRequest();
            request.setEnergyDistributionEvent(device.getEnergyDistributionEvent());
            request.setDeviceType(device.getDeviceType());
            request.setPriority(1);
            request.setName(device.getName());

            // when
            Device updatedDevice = deviceService.update(device.getId(), request);

            // then
            assertEquals(1, updatedDevice.getPriority());

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
            assertEquals(2, deviceTypes.size());
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