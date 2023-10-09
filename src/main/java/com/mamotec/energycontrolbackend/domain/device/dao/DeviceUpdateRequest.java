package com.mamotec.energycontrolbackend.domain.device.dao;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.mamotec.energycontrolbackend.domain.device.DeviceType;
import com.mamotec.energycontrolbackend.domain.device.EnergyDistributionEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "deviceType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = DeviceUpdateRequest.class, name = "HYBRID_INVERTER"),
        @JsonSubTypes.Type(value = DeviceUpdateRequest.class, name = "CHARGING_STATION"),
})
public class DeviceUpdateRequest {

    private int priority;
    private String name;

    private DeviceType deviceType;
    private EnergyDistributionEvent energyDistributionEvent;

}
