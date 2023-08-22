package com.mamotec.energycontrolbackend.domain.interfaceconfig.yaml;

import com.mamotec.energycontrolbackend.domain.device.DeviceType;
import lombok.Data;

@Data
public class DeviceYaml {
    private long deviceId;
    private DeviceType deviceType;
    private String name;
    private InterfaceConnectionYaml connection;
    private InterfaceMappingYaml mapping;
}
