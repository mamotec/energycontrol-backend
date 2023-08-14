package com.mamotec.energycontrolbackend.domain.device.dao;

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

    private Integer unitId;

    private InterfaceConfig interfaceConfig;
}
