package com.mamotec.energycontrolbackend.mapper;

import com.mamotec.energycontrolbackend.domain.group.DeviceGroup;
import com.mamotec.energycontrolbackend.domain.group.PlantDeviceGroup;
import com.mamotec.energycontrolbackend.domain.group.dao.DeviceGroupCreate;
import com.mamotec.energycontrolbackend.domain.group.dao.PlantDeviceGroupCreate;
import org.mapstruct.*;

@Mapper(componentModel = "spring", subclassExhaustiveStrategy = SubclassExhaustiveStrategy.RUNTIME_EXCEPTION)
public interface DeviceGroupMapper {

    @SubclassMapping(source = PlantDeviceGroupCreate.class, target = PlantDeviceGroup.class)
    DeviceGroup map(DeviceGroupCreate deviceGroupCreate);

}
