package com.mamotec.energycontrolbackend.domain.interfaceconfig.dao;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RegisterMapping {

    private List<Integer> register;

    @Enumerated(EnumType.STRING)
    private Unit unit;

    private String type;

    @Enumerated(EnumType.STRING)
    private FunctionCode fc;
}
