package com.mamotec.energycontrolbackend.domain.interfaceconfig.dao;

import lombok.Data;

import java.util.List;

@Data
public class Interfaces {

    private int version;
    private List<Interface> interfaces;
}
