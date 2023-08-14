package com.mamotec.energycontrolbackend.domain.interfaceconfig.dao;

import com.mamotec.energycontrolbackend.domain.interfaceconfig.InterfaceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InterfaceConfigDao {

    private long protocolId;
    private InterfaceType type;
    private String port;
}
