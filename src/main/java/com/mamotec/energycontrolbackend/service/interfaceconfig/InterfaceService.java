package com.mamotec.energycontrolbackend.service.interfaceconfig;

import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.device.DeviceType;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.yaml.DeviceYaml;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.yaml.InterfaceYaml;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.yaml.ManufacturerYaml;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InterfaceService {

    private final InterfaceReader reader;

    public List<InterfaceYaml> getAllInterfaces(DeviceType deviceType) {
        InputStream is = getClass().getResourceAsStream("/interfaceConfig.yaml");

        List<InterfaceYaml> interfaceYamls = reader.readInterface(is);

        return interfaceYamls.stream()
                .filter(i -> i.getDevices()
                        .stream()
                        .anyMatch(d -> d.getDeviceType() == deviceType))
                .toList();

    }

    public DeviceYaml getDeviceInformationForManufactureAndDeviceId(Device device) {
        List<InterfaceYaml> allInterfaces = getAllInterfaces(device.getDeviceType());
        for (InterfaceYaml i : allInterfaces) {
            if (i.getMetaData()
                    .getManufacturer()
                    .getManufacturerId() == device.getManufacturerId()) {
                List<DeviceYaml> devices = i.getDevices();
                for (DeviceYaml d : devices) {
                    if (d.getDeviceId() == device.getDeviceId()) {
                        return d;
                    }
                }
            }
        }
        return null;
    }


    public List<ManufacturerYaml> getAllManufactures(DeviceType deviceType) {
        List<InterfaceYaml> allInterfaces = getAllInterfaces(deviceType);
        List<ManufacturerYaml> manufactures = new ArrayList<>();
        for (InterfaceYaml i : allInterfaces) {
            manufactures.add(i.getMetaData()
                    .getManufacturer());
        }

        return manufactures;
    }

    public List<DeviceYaml> getAllDevicesByManufacturerAndDeviceType(Long manufacturerId, DeviceType deviceType) {
        List<InterfaceYaml> allInterfaces = getAllInterfaces(deviceType);
        List<DeviceYaml> devices = new ArrayList<>();
        for (InterfaceYaml i : allInterfaces) {
            if (i.getMetaData()
                    .getManufacturer()
                    .getManufacturerId() == manufacturerId) {
                for (DeviceYaml d : i.getDevices()) {
                    if (d.getDeviceType() == deviceType) {
                        devices.add(d);
                    }
                }
            }
        }
        return devices;
    }

    public String getDeviceNameByManufacturerAndDeviceId(Device device) {
        List<InterfaceYaml> allInterfaces = getAllInterfaces(device.getDeviceType());
        for (InterfaceYaml i : allInterfaces) {
            if (i.getMetaData()
                    .getManufacturer()
                    .getManufacturerId() == device.getManufacturerId()) {
                for (DeviceYaml d : i.getDevices()) {
                    if (d.getDeviceId() == device.getDeviceId()) {
                        return d.getName();
                    }
                }
            }
        }
        return null;
    }
}
