package com.mamotec.energycontrolbackend.mapper;

import com.mamotec.energycontrolbackend.domain.interfaceconfig.InterfaceConfig;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.dao.InterfaceConfigDao;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InterfaceConfigMapper {

    InterfaceConfig map(InterfaceConfigDao interfaceConfigDao);

    InterfaceConfigDao map(InterfaceConfig interfaceConfig);
}
