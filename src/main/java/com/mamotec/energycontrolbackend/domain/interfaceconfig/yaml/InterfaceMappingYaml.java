package com.mamotec.energycontrolbackend.domain.interfaceconfig.yaml;

import lombok.Data;

@Data
public class InterfaceMappingYaml {

    private RegisterMapping power;
    private RegisterMapping powerReduction;

}
