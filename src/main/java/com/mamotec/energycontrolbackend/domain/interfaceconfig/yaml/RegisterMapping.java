package com.mamotec.energycontrolbackend.domain.interfaceconfig.yaml;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.util.List;

@Data
public class RegisterMapping {

    private List<Integer> register;

    @Enumerated(EnumType.STRING)
    private Unit unit;

    private String type;

    @Enumerated(EnumType.STRING)
    private FunctionCode fc;

    @Enumerated(EnumType.STRING)
    private ConversionMethod conversionMethod;
}
