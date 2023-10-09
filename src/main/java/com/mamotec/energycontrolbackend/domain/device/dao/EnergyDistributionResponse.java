package com.mamotec.energycontrolbackend.domain.device.dao;

import com.mamotec.energycontrolbackend.domain.device.EnergyDistributionEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnergyDistributionResponse {

    private EnergyDistributionEvent event;
    private String description;
}
