package com.mamotec.energycontrolbackend.factory;

import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.device.DeviceType;
import com.mamotec.energycontrolbackend.domain.device.TcpDevice;
import com.mamotec.energycontrolbackend.domain.group.DeviceGroup;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.InterfaceConfig;
import com.mamotec.energycontrolbackend.repository.DeviceRepository;

public final class DeviceFactory {

    public static TcpDevice aTcpDevice(InterfaceConfig interfaceConfig) {
        TcpDevice tcp = new TcpDevice();
        tcp.setInterfaceConfig(interfaceConfig);
        tcp.setDeviceType(DeviceType.INVERTER);
        tcp.setName("TestDevice");
        tcp.setHost("localhost");
        tcp.setPort("502");
        return tcp;
    }

    public static Device aTcpDevice(InterfaceConfig interfaceConfig, DeviceRepository deviceRepository) {
        return deviceRepository.save(aTcpDevice(interfaceConfig));
    }

    public static Device aTcpDevice(InterfaceConfig interfaceConfig, DeviceGroup group, DeviceRepository deviceRepository) {
        TcpDevice tcpDevice = aTcpDevice(interfaceConfig);
        tcpDevice.setDeviceGroup(group);
        return deviceRepository.save(tcpDevice);
    }
}
