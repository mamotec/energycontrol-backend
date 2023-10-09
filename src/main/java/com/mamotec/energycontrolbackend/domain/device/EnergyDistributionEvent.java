package com.mamotec.energycontrolbackend.domain.device;

import lombok.Getter;

import java.util.List;

@Getter
public enum EnergyDistributionEvent {

    RENEWABLE_ENERGY("Erneuerbare Energie", "Verbraucher wird nur mit erneuerbarer Energie geladen.", DeviceType.BATTERY, DeviceType.CHARGING_STATION, DeviceType.HEAT_PUMP),
    UNMANAGED("Immer laden", "Verbraucher wird kontinuierlich geladen, ohne Regulierung durch das Energiemanagementsystem.", DeviceType.BATTERY, DeviceType.CHARGING_STATION, DeviceType.HEAT_PUMP);

    private final List<DeviceType> deviceTypes;
    private final String description;
    private final String name;

    EnergyDistributionEvent(String name, String description, DeviceType... deviceType) {
        this.deviceTypes = List.of(deviceType);
        this.description = description;
        this.name = name;
    }

}
