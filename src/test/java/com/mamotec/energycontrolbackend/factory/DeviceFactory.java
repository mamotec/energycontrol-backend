package com.mamotec.energycontrolbackend.factory;

import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.device.DeviceType;
import com.mamotec.energycontrolbackend.domain.device.HybridInverterDevice;
import com.mamotec.energycontrolbackend.domain.group.DeviceGroup;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.InterfaceConfig;
import com.mamotec.energycontrolbackend.repository.DeviceRepository;

public final class DeviceFactory {

    public static Device aDevice(InterfaceConfig interfaceConfig) {
        HybridInverterDevice tcp = new HybridInverterDevice();
        tcp.setInterfaceConfig(interfaceConfig);
        tcp.setName("TestDevice");
        tcp.setHost("localhost");
        tcp.setPort("502");
        return tcp;
    }

    public static Device aDevice(InterfaceConfig interfaceConfig, DeviceRepository deviceRepository) {
        return deviceRepository.save(aDevice(interfaceConfig));
    }

    public static Device aDevice(InterfaceConfig interfaceConfig, DeviceGroup group, DeviceRepository deviceRepository) {
        Device tcpDevice = aDevice(interfaceConfig);
        tcpDevice.setDeviceGroup(group);
        return deviceRepository.save(tcpDevice);
    }
}
