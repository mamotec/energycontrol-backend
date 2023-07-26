package com.mamotec.energycontrolbackend.domain.modbus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ModbusType {

    RTU(247),
    TCP(65535);

    private final long maxNumberOfSlaves;



}
