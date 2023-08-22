package com.mamotec.energycontrolbackend.domain.interfaceconfig.yaml;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class InterfaceConnectionYaml {

    private int baudRate;
    @Enumerated(EnumType.STRING)
    private Parity parity;
    private int dataBits;
    private int stopBits;
}
