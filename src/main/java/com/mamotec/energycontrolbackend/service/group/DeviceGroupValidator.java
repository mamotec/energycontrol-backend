package com.mamotec.energycontrolbackend.service.group;

import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.group.DeviceGroup;
import com.mamotec.energycontrolbackend.exception.AddDeviceToGroupException;

public final class DeviceGroupValidator {

    private DeviceGroupValidator() throws InstantiationException {
        throw new InstantiationException("Utility classes should not be instantiated");
    }

    public static void validate(DeviceGroup deviceGroup, Device device) {
        if (!deviceGroup.getType()
                .canAddDeviceToGroup(device.getDeviceType())) {
            throw new AddDeviceToGroupException("Device type " + device.getDeviceType() + " not allowed in group " + deviceGroup.getType());

        }

    }
}
