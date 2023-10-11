package com.mamotec.energycontrolbackend.domain.device;

import com.mamotec.energycontrolbackend.domain.group.DeviceGroupType;
import lombok.Getter;
import lombok.Setter;

import static com.mamotec.energycontrolbackend.domain.group.DeviceGroupType.HOME;
import static com.mamotec.energycontrolbackend.domain.group.DeviceGroupType.PLANT;

@Getter
public enum DeviceType {

    INVERTER,
    HYBRID_INVERTER,
    CHARGING_STATION,
    HEAT_PUMP,
    BATTERY;

    static {
        INVERTER.setGroup(PLANT);
        HYBRID_INVERTER.setGroup(HOME);
        CHARGING_STATION.setGroup(DeviceGroupType.CHARGING_STATION);
        HEAT_PUMP.setGroup(DeviceGroupType.HEAT_PUMP);
        BATTERY.setGroup(PLANT);
    }

    DeviceGroupType group;

    private void setGroup(DeviceGroupType group) {
        this.group = group;
    }



}
