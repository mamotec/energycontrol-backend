package com.mamotec.energycontrolbackend.domain.device;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public enum EnergyDistributionEvent {

    RENEWABLE_ENERGY("Erneuerbare Energie", "Verbraucher wird nur mit erneuerbarer Energie geladen."),
    UNMANAGED("Immer laden", "Verbraucher wird kontinuierlich geladen, ohne Regulierung durch das Energiemanagementsystem.");

    static {
        RENEWABLE_ENERGY.deviceTypes.addAll(List.of(DeviceType.BATTERY, DeviceType.CHARGING_STATION, DeviceType.HEAT_PUMP));
        UNMANAGED.deviceTypes.addAll(List.of(DeviceType.BATTERY, DeviceType.CHARGING_STATION, DeviceType.HEAT_PUMP));
    }

    final List<DeviceType> deviceTypes = new ArrayList<>();
    private final String description;
    private final String name;

    EnergyDistributionEvent(String name, String description) {
        this.description = description;
        this.name = name;
    }

}
