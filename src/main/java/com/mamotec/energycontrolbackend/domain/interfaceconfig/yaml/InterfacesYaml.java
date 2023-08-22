package com.mamotec.energycontrolbackend.domain.interfaceconfig.yaml;

import lombok.Data;

import java.util.List;

@Data
public class InterfacesYaml {

    private int version;
    private List<InterfaceYaml> interfaces;
}
