package com.mamotec.energycontrolbackend.domain.group.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnergyDeviceGroupCreate extends DeviceGroupCreate {

    private long peakKilowatt;

}

