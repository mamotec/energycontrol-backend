package com.mamotec.energycontrolbackend.domain.interfaceconfig;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum InterfaceType {

    RS485(32),
    TCP(247);

    private final int maxDevices;
}
