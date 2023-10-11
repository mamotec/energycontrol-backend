package com.mamotec.energycontrolbackend.factory;

import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.device.HybridInverterDevice;
import com.mamotec.energycontrolbackend.domain.device.chargingstation.ChargingStationDevice;
import com.mamotec.energycontrolbackend.domain.group.DeviceGroup;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.InterfaceConfig;
import com.mamotec.energycontrolbackend.repository.DeviceRepository;

public final class DeviceFactory {

    public static HybridInverterDevice aHybridInverter(InterfaceConfig interfaceConfig) {
        HybridInverterDevice device = new HybridInverterDevice();
        device.setInterfaceConfig(interfaceConfig);
        device.setName("TestDevice");
        device.setHost("localhost");
        device.setPort("502");
        return device;
    }

    public static HybridInverterDevice aHybridInverter(InterfaceConfig interfaceConfig, DeviceRepository deviceRepository) {
        return deviceRepository.save(aHybridInverter(interfaceConfig));
    }

    public static Device aHybridInverter(InterfaceConfig interfaceConfig, DeviceGroup group, DeviceRepository deviceRepository) {
        Device tcpDevice = aHybridInverter(interfaceConfig);
        tcpDevice.setDeviceGroup(group);
        return deviceRepository.save(tcpDevice);
    }

    public static ChargingStationDevice aChargingStation() {
        ChargingStationDevice device = new ChargingStationDevice();
        device.setOcppAvailable(true);
        device.setDeviceIdCharger(1);
        return device;
    }

    public static ChargingStationDevice aChargingStation(DeviceRepository deviceRepository) {
        return deviceRepository.save(aChargingStation());
    }

}
