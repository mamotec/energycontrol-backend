package com.mamotec.energycontrolbackend.service.interfaceconfig;

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

    public List<InterfaceYaml> getAllInterfaces() {
        InputStream is = getClass().getResourceAsStream("/interfaceConfig.yaml");

        return reader.readInterface(is);

    }

    public DeviceYaml getDeviceInformationForManufactureAndDeviceId(long manufactureId, long deviceId) {
        List<InterfaceYaml> allInterfaces = getAllInterfaces();
        for (InterfaceYaml i : allInterfaces) {
            if (i.getMetaData()
                    .getManufacturer()
                    .getManufacturerId() == manufactureId) {
                List<DeviceYaml> devices = i.getDevices();
                for (DeviceYaml d : devices) {
                    if (d.getDeviceId() == deviceId) {
                        return d;
                    }
                }
            }
        }
        return null;
    }


    public List<ManufacturerYaml> getAllManufactures() {
        List<InterfaceYaml> allInterfaces = getAllInterfaces();
        List<ManufacturerYaml> manufactures = new ArrayList<>();
        for (InterfaceYaml i : allInterfaces) {
            manufactures.add(i.getMetaData()
                    .getManufacturer());
        }

        return manufactures;
    }

    public List<DeviceYaml> getAllDevicesByManufacturerAndDeviceType(Long manufacturerId, DeviceType deviceType) {
        List<InterfaceYaml> allInterfaces = getAllInterfaces();
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

    public String getDeviceNameByManufacturerAndDeviceId(Long manufacturerId, Long deviceId) {
        List<InterfaceYaml> allInterfaces = getAllInterfaces();
        for (InterfaceYaml i : allInterfaces) {
            if (i.getMetaData()
                    .getManufacturer()
                    .getManufacturerId() == manufacturerId) {
                for (DeviceYaml d : i.getDevices()) {
                    if (d.getDeviceId() == deviceId) {
                        return d.getName();
                    }
                }
            }
        }
        return null;
    }
}
