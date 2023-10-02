package com.mamotec.energycontrolbackend.domain.device.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TcpDeviceCreateRequest extends DeviceCreateRequest {

    private String host;
    private String port;

}
