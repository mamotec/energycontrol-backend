package com.mamotec.energycontrolbackend.factory;

import com.mamotec.energycontrolbackend.domain.group.PlantDeviceGroup;
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
}
