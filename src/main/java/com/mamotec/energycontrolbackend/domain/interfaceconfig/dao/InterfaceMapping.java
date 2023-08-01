package com.mamotec.energycontrolbackend.domain.interfaceconfig.dao;

import lombok.Data;

@Data
public class InterfaceMapping {

    private RegisterMapping power;
    private RegisterMapping powerReduction;

}
