package com.mamotec.energycontrolbackend.domain.interfaceconfig.dao;

import lombok.Data;

@Data
public class Interface {

    private int version;
    private MetaData metaData;
    private InterfaceConnection connection;
    private InterfaceMapping mapping;
}
