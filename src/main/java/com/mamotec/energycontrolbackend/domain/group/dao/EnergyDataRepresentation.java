package com.mamotec.energycontrolbackend.domain.group.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnergyDataRepresentation extends DeviceGroupRepresentation {

    private long activePower;
    private long peakKilowatt;
}
