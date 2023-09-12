package com.mamotec.energycontrolbackend.mapper;

import com.mamotec.energycontrolbackend.domain.group.DeviceGroup;
import com.mamotec.energycontrolbackend.domain.group.PlantDeviceGroup;
import com.mamotec.energycontrolbackend.domain.group.dao.DeviceGroupCreate;
import com.mamotec.energycontrolbackend.domain.group.dao.DeviceGroupUpdate;
import com.mamotec.energycontrolbackend.domain.group.dao.PlantDeviceGroupCreate;
import com.mamotec.energycontrolbackend.domain.group.dao.PlantDeviceGroupUpdate;
import org.mapstruct.Mapper;
import org.mapstruct.SubclassExhaustiveStrategy;
import org.mapstruct.SubclassMapping;

@Mapper(config = DeviceGroupMapperConfig.class, componentModel = "spring", subclassExhaustiveStrategy = SubclassExhaustiveStrategy.RUNTIME_EXCEPTION)
public interface DeviceGroupMapper {

    @SubclassMapping(source = PlantDeviceGroupCreate.class, target = PlantDeviceGroup.class)
    DeviceGroup map(DeviceGroupCreate deviceGroupCreate);

    @SubclassMapping(source = PlantDeviceGroupUpdate.class, target = PlantDeviceGroup.class)
    DeviceGroup map(DeviceGroupUpdate deviceGroupUpdate);

}
