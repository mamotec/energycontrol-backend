package com.mamotec.energycontrolbackend.mapper;

import com.mamotec.energycontrolbackend.domain.interfaceconfig.InterfaceConfig;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.dao.InterfaceConfigDao;
import com.mamotec.energycontrolbackend.service.interfaceconfig.InterfaceService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class InterfaceConfigMapper {

    @Autowired
    private InterfaceService interfaceService;

    @Mapping(target = "protocolId", source = "protocolId")
    public abstract InterfaceConfig map(InterfaceConfigDao interfaceConfigDao);

    @Mapping(target = "protocolId", source = "protocolId")
    @Mapping(target = "protocolName", expression = "java(getProtocolName(interfaceConfig))")
    public abstract InterfaceConfigDao map(InterfaceConfig interfaceConfig);

    public abstract List<InterfaceConfigDao> map(List<InterfaceConfig> interfaceConfig);

    String getProtocolName(InterfaceConfig interfaceConfig) {
        return interfaceService.getInterfaceByProtocolId(interfaceConfig.getProtocolId())
                .getMetaData()
                .getDescription();
    }
}
