package com.mamotec.energycontrolbackend.domain.device.dao;

import com.mamotec.energycontrolbackend.domain.device.DeviceType;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.InterfaceConfig;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeviceCreateRequest {

    private InterfaceConfig interfaceConfig;
    private long unitId;
    private long manufacturerId;
    private DeviceType deviceType;
    private String name;
    private long deviceId;

}
