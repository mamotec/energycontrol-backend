package com.mamotec.energycontrolbackend.domain.group.dao;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PlantDeviceGroupUpdate.class, name = "PLANT"),
})
public class DeviceGroupUpdate extends DeviceGroupCreate {

    private Long id;

}
