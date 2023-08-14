package com.mamotec.energycontrolbackend.domain.interfaceconfig;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@Schema(description = "Die Schnittstellen Typen")
public enum InterfaceType {

    RS485(32),
    TCP(247);

    private final int maxDevices;
}
