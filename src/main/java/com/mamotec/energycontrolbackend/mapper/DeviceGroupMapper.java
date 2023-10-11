package com.mamotec.energycontrolbackend.mapper;

import com.mamotec.energycontrolbackend.domain.group.DeviceGroup;
import com.mamotec.energycontrolbackend.domain.group.dao.DeviceGroupCreate;
import com.mamotec.energycontrolbackend.domain.group.dao.DeviceGroupUpdate;
import com.mamotec.energycontrolbackend.domain.group.dao.home.HomeDeviceGroup;
import com.mamotec.energycontrolbackend.domain.group.dao.home.HomeDeviceGroupCreate;
import com.mamotec.energycontrolbackend.domain.group.dao.plant.PlantDeviceGroup;
import com.mamotec.energycontrolbackend.domain.group.dao.plant.PlantDeviceGroupCreate;
import com.mamotec.energycontrolbackend.domain.group.dao.plant.PlantDeviceGroupUpdate;
import org.mapstruct.Mapper;
import org.mapstruct.SubclassExhaustiveStrategy;
import org.mapstruct.SubclassMapping;

@Mapper(config = DeviceGroupMapperConfig.class, componentModel = "spring", subclassExhaustiveStrategy = SubclassExhaustiveStrategy.RUNTIME_EXCEPTION)
public interface DeviceGroupMapper {

    @SubclassMapping(source = PlantDeviceGroupCreate.class, target = PlantDeviceGroup.class)
    @SubclassMapping(source = HomeDeviceGroupCreate.class, target = HomeDeviceGroup.class)
    DeviceGroup map(DeviceGroupCreate deviceGroupCreate);

    @SubclassMapping(source = PlantDeviceGroupUpdate.class, target = PlantDeviceGroup.class)
    DeviceGroup map(DeviceGroupUpdate deviceGroupUpdate);

}
