package com.mamotec.energycontrolbackend.domain.device.dao;

import com.mamotec.energycontrolbackend.domain.device.DeviceType;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class DeviceTypeResponse {
    private DeviceType deviceType;
    private String label;
}
