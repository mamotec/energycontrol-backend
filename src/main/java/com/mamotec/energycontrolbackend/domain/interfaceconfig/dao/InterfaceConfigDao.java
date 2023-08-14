package com.mamotec.energycontrolbackend.domain.interfaceconfig.dao;

import com.mamotec.energycontrolbackend.domain.interfaceconfig.InterfaceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InterfaceConfigDao {

    private Long id;
    private long protocolId;
    private String protocolName;
    private InterfaceType type;
    private String port;

}
