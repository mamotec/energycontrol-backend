package com.mamotec.energycontrolbackend.domain.device;

import com.mamotec.energycontrolbackend.domain.group.DeviceGroupType;
import lombok.Getter;

import static com.mamotec.energycontrolbackend.domain.group.DeviceGroupType.HOME;

@Getter
public enum DeviceType {

    INVERTER(null),
    HYBRID_INVERTER(HOME),
    CHARGING_STATION(DeviceGroupType.CHARGING_STATION),
    HEAT_PUMP(DeviceGroupType.HEAT_PUMP),
    BATTERY(null);

    private final DeviceGroupType group;

    DeviceType(DeviceGroupType group) {
        this.group = group;
    }



}
