package com.mamotec.energycontrolbackend.service.interfaceconfig;

import com.mamotec.energycontrolbackend.domain.device.DeviceType;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.yaml.DeviceYaml;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.yaml.InterfaceYaml;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.yaml.ManufacturerYaml;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class InterfaceServiceTest {

    @Autowired
    private InterfaceService service;

    @Test
    void shouldGetAllInterfaces() {
        // when
        List<InterfaceYaml> interfaceConfigs = service.getAllInterfaces();
        // then
        assertNotNull(interfaceConfigs);
        assertEquals(1, interfaceConfigs.size());
    }

    @Test
    void shouldGetDeviceInformationForManufacturerAndDeviceId() {
        // when
        DeviceYaml deviceInformation = service.getDeviceInformationForManufactureAndDeviceId(1L, 1L);
        // then
        assertNotNull(deviceInformation);
    }

    @Test
    void shouldGetAllManufactures() {
        // when
        List<ManufacturerYaml> manufactures = service.getAllManufactures();
        // then
        assertNotNull(manufactures);
        assertEquals(1, manufactures.size());
    }

    @Test
    void shouldGetAllDevicesByManufacturerAndDeviceType() {
        // when
        List<DeviceYaml> devices = service.getAllDevicesByManufacturerAndDeviceType(1L, DeviceType.HYBRID_INVERTER);
        // then
        assertNotNull(devices);
        assertEquals(5, devices.size());
    }

    @Test
    void shouldGetDeviceNameByManufacturerAndDeviceId() {
        // when
        String deviceName = service.getDeviceNameByManufacturerAndDeviceId(1L, 1L);
        // then
        assertNotNull(deviceName);


    }
}