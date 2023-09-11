package com.mamotec.energycontrolbackend.mapper;

import com.mamotec.energycontrolbackend.domain.group.DeviceGroup;
import com.mamotec.energycontrolbackend.domain.group.dao.DeviceGroupCreate;
import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;
import org.mapstruct.MappingInheritanceStrategy;

@MapperConfig
public interface DeviceGroupMapperConfig {

    @Mapping(target = "name", source = "name")
    DeviceGroup map(DeviceGroupCreate deviceGroupCreate);

}
