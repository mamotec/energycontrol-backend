package com.mamotec.energycontrolbackend.factory;

import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.InterfaceConfig;

public final class DeviceFactory {

    public static Device aDevice(InterfaceConfig interfaceConfig) {
        return Device.builder()
                .interfaceConfig(interfaceConfig)
                .unitId(1)
                .name("TestDevice")
                .build();
    }
}
