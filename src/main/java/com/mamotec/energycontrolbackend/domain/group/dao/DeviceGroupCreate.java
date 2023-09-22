package com.mamotec.energycontrolbackend.domain.group.dao;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.group.DeviceGroupType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = EnergyDeviceGroupCreate.class, name = "PLANT"),
        @JsonSubTypes.Type(value = EnergyDeviceGroupCreate.class, name = "HOME"),
})
public class DeviceGroupCreate {

    private String name;
    private List<Device> devices;
    private DeviceGroupType type;
}
