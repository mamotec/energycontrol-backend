package com.mamotec.energycontrolbackend.factory;

import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.device.DeviceType;
import com.mamotec.energycontrolbackend.domain.group.DeviceGroup;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.InterfaceConfig;
import com.mamotec.energycontrolbackend.repository.DeviceRepository;

public final class DeviceFactory {

    public static Device aDevice(InterfaceConfig interfaceConfig) {
        return Device.builder()
                .interfaceConfig(interfaceConfig)
                .deviceType(DeviceType.INVERTER)
                .unitId(1)
                .name("TestDevice")
                .build();
    }
    public static Device aDevice(InterfaceConfig interfaceConfig, int slaveId) {
        return Device.builder()
                .interfaceConfig(interfaceConfig)
                .deviceType(DeviceType.INVERTER)
                .unitId(slaveId)
                .name("TestDevice")
                .build();
    }

    public static Device aDevice(InterfaceConfig interfaceConfig, Integer unitId, DeviceRepository deviceRepository) {
        return deviceRepository.save(Device.builder()
                .interfaceConfig(interfaceConfig)
                .deviceType(DeviceType.INVERTER)
                .unitId(unitId)
                .name("TEST")
                .build());
    }

    public static Device aDevice(InterfaceConfig interfaceConfig, DeviceGroup group, Integer unitId, DeviceRepository deviceRepository) {
        return deviceRepository.save(Device.builder()
                .interfaceConfig(interfaceConfig)
                .unitId(unitId)
                .deviceType(DeviceType.INVERTER)
                .deviceGroup(group)
                .name("TEST")
                .build());
    }
}
