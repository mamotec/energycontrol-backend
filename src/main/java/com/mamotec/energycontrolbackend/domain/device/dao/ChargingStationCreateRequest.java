package com.mamotec.energycontrolbackend.domain.device.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChargingStationCreateRequest extends DeviceCreateRequest {

    private long deviceIdCharger;
    private boolean ocppAvailable;

}
