package com.mamotec.energycontrolbackend.domain.interfaceconfig.yaml;

import lombok.Data;

import java.util.List;

@Data
public class InterfaceYaml {

    private MetaDataYaml metaData;
    private List<DeviceYaml> devices;
    private InterfaceConnectionYaml connection;
    private InterfaceMappingYaml mapping;

}
