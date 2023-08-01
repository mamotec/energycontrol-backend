package com.mamotec.energycontrolbackend.domain.interfaceconfig.dao;

import lombok.Getter;

@Getter
public enum FunctionCode {
    READ_COILS(1),
    READ_DISCRETE_INPUTS(2),
    READ_HOLDING_REGISTERS(3),
    READ_INPUT_REGISTERS(4),
    WRITE_SINGLE_COIL(5),
    WRITE_SINGLE_REGISTER(6),
    WRITE_MULTIPLE_COILS(15),
    WRITE_MULTIPLE_REGISTERS(16);

    private final int code;

    FunctionCode(int code) {
        this.code = code;
    }

}
