package com.mamotec.energycontrolbackend.mapper;

import com.mamotec.energycontrolbackend.domain.interfaceconfig.InterfaceConfig;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.dao.InterfaceConfigDao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InterfaceConfigMapper {

    @Mapping(target = "protocolId", source = "protocolId")
    InterfaceConfig map(InterfaceConfigDao interfaceConfigDao);

    @Mapping(target = "protocolId", source = "protocolId")
    InterfaceConfigDao map(InterfaceConfig interfaceConfig);

    List<InterfaceConfigDao> map(List<InterfaceConfig> interfaceConfig);
}
