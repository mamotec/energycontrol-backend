package com.mamotec.energycontrolbackend.mapper;

import com.mamotec.energycontrolbackend.domain.group.DeviceGroup;
import com.mamotec.energycontrolbackend.domain.group.dao.DeviceGroupCreate;
import com.mamotec.energycontrolbackend.domain.group.dao.DeviceGroupUpdate;
import org.mapstruct.*;

@MapperConfig
public interface DeviceGroupMapperConfig {

    @Mapping(target = "name", source = "name")
    DeviceGroup map(DeviceGroupCreate deviceGroupCreate);

    @InheritConfiguration
    @Mapping(target = "id", source = "id")
    DeviceGroup map(DeviceGroupUpdate deviceGroupUpdate);

}
