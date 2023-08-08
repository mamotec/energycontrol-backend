package com.mamotec.energycontrolbackend.domain.interfaceconfig.dao;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class InterfaceConnection {

    private int baudRate;
    @Enumerated(EnumType.STRING)
    private Parity parity;
    private int dataBits;
    private int stopBits;
    private String host;
    private int port;
}
