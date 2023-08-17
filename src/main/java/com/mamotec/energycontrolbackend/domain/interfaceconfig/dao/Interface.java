package com.mamotec.energycontrolbackend.domain.interfaceconfig.dao;

import lombok.Data;

@Data
public class Interface {
    private MetaData metaData;
    private InterfaceConnection connection;
    private InterfaceMapping mapping;
}
