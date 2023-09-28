package com.mamotec.energycontrolbackend.domain.interfaceconfig.yaml;

import lombok.Data;

@Data
public class InterfaceMappingYaml {

    private RegisterMapping power;
    private RegisterMapping batterySoc;
    private RegisterMapping batteryPower;
    private RegisterMapping powerReduction;
    private RegisterMapping gridPower;

}
