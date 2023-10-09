package com.mamotec.energycontrolbackend.domain.device;

import lombok.Getter;

import java.util.List;

@Getter
public enum EnergyDistributionEvent {

    SOLAR("Verbraucher wird nur mit Solarenergie geladen.", DeviceType.BATTERY, DeviceType.CHARGING_STATION, DeviceType.HEAT_PUMP),
    SOLAR_BATTERY("Verbraucher wird mit Solarenergie und Batterie geladen.", DeviceType.BATTERY, DeviceType.CHARGING_STATION, DeviceType.HEAT_PUMP),

    private final List<DeviceType> deviceTypes;
    private final String description;

    EnergyDistributionEvent(String description, DeviceType... deviceType) {
        this.deviceTypes = List.of(deviceType);
        this.description = description;
    }


}
