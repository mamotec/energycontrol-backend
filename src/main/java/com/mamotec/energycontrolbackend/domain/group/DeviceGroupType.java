package com.mamotec.energycontrolbackend.domain.group;

import com.mamotec.energycontrolbackend.domain.device.DeviceType;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public enum DeviceGroupType {

    PLANT,
    CHARGING_STATION,
    HEAT_PUMP,
    BATTERY,
    HOME;

    static {
        PLANT.setValidDeviceTypes(Arrays.asList(DeviceType.INVERTER, DeviceType.HYBRID_INVERTER));
        CHARGING_STATION.setValidDeviceTypes(List.of(DeviceType.CHARGING_STATION));
        HEAT_PUMP.setValidDeviceTypes(List.of(DeviceType.HEAT_PUMP));
        BATTERY.setValidDeviceTypes(List.of(DeviceType.BATTERY));
        HOME.setValidDeviceTypes(List.of(DeviceType.HYBRID_INVERTER));
    }

    final List<DeviceType> validDeviceTypes = new ArrayList<>();

    public boolean canAddDeviceToGroup(DeviceType type) {
        return validDeviceTypes.contains(type);
    }

    private void setValidDeviceTypes(List<DeviceType> validDeviceTypes) {
        this.validDeviceTypes.addAll(validDeviceTypes);
    }

}
