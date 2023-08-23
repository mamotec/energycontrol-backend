package com.mamotec.energycontrolbackend.domain.device;

import lombok.Getter;

@Getter
public enum DeviceType {

    INVERTER("Wechselrichter");

    DeviceType(String descriptionDe) {
        this.descriptionDe = descriptionDe;
    }

    private final String descriptionDe;
}
