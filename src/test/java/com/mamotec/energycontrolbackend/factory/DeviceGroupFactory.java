package com.mamotec.energycontrolbackend.factory;

import com.mamotec.energycontrolbackend.domain.group.dao.home.HomeDeviceGroup;
import com.mamotec.energycontrolbackend.domain.group.dao.plant.PlantDeviceGroup;
import com.mamotec.energycontrolbackend.repository.DeviceGroupRepository;

public final class DeviceGroupFactory {

    public static PlantDeviceGroup aPlantDeviceGroup() {
        PlantDeviceGroup c = new PlantDeviceGroup();
        c.setName("PV - Rechte Seiete");
        c.setDirectMarketing(true);
        return c;
    }

    public static PlantDeviceGroup aPlantDeviceGroup(final DeviceGroupRepository deviceGroupRepository) {
        return deviceGroupRepository.save(aPlantDeviceGroup());
    }

    public static HomeDeviceGroup aHomeDeviceGroup() {
        HomeDeviceGroup c = new HomeDeviceGroup();
        c.setName("PV - Rechte Seiete");
        c.setPeakKilowatt(1);
        return c;
    }

    public static HomeDeviceGroup aHomeDeviceGroup(final DeviceGroupRepository deviceGroupRepository) {
        return deviceGroupRepository.save(aHomeDeviceGroup());
    }
}
