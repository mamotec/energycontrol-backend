package com.mamotec.energycontrolbackend.domain.device.dao;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.mamotec.energycontrolbackend.domain.device.DeviceType;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.InterfaceConfig;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.InterfaceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "interfaceType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = SerialDeviceCreateRequest.class, name = "RS485"),
        @JsonSubTypes.Type(value = TcpDeviceCreateRequest.class, name = "TCP"),
})
public class DeviceCreateRequest {

    private InterfaceConfig interfaceConfig;
    private DeviceType deviceType;
    private String name;
    private InterfaceType interfaceType;
    private long manufacturerId;
    private long deviceId;
    private long unitId;
    private long peakKilowatt;

}
