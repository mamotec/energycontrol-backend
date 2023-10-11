package com.mamotec.energycontrolbackend.service.device;

import com.mamotec.energycontrolbackend.domain.group.DeviceGroup;
import com.mamotec.energycontrolbackend.domain.group.DeviceGroupType;
import com.mamotec.energycontrolbackend.domain.group.dao.battery.BatteryDeviceGroup;
import com.mamotec.energycontrolbackend.domain.group.dao.chargingstation.ChargingStationDeviceGroup;
import com.mamotec.energycontrolbackend.domain.group.dao.heatpump.HeatPumpDeviceGroup;
import com.mamotec.energycontrolbackend.domain.group.dao.home.HomeDeviceGroup;
import com.mamotec.energycontrolbackend.domain.group.dao.plant.PlantDeviceGroup;

public class DeviceGroupFactory {

    public static DeviceGroup getDeviceGroup(DeviceGroupType group) {
        return switch (group) {
            case HOME -> new HomeDeviceGroup();
            case PLANT -> new PlantDeviceGroup();
            case BATTERY -> new BatteryDeviceGroup();
            case HEAT_PUMP -> new HeatPumpDeviceGroup();
            case CHARGING_STATION -> new ChargingStationDeviceGroup();
        };
    }
}
