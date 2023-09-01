package com.mamotec.energycontrolbackend.factory;

import com.mamotec.energycontrolbackend.domain.group.DeviceGroup;
import com.mamotec.energycontrolbackend.domain.group.DeviceGroupType;
import com.mamotec.energycontrolbackend.repository.DeviceGroupRepository;

public final class DeviceGroupFactory {

    public static DeviceGroup aDeviceGroup() {
        DeviceGroup c = new DeviceGroup();
        c.setName("PV - Rechte Seiete");
        c.setType(DeviceGroupType.PV_PLANT);
        return c;
    }

    public static DeviceGroup aDeviceGroup(final DeviceGroupRepository deviceGroupRepository) {
        return deviceGroupRepository.save(aDeviceGroup());
    }
}
