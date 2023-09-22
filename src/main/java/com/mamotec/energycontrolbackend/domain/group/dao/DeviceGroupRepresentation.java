package com.mamotec.energycontrolbackend.domain.group.dao;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.mamotec.energycontrolbackend.domain.group.DeviceGroupType;
import com.mamotec.energycontrolbackend.domain.group.dao.home.HomeDataRepresentation;
import com.mamotec.energycontrolbackend.domain.group.dao.plant.PlantDataRepresentation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PlantDataRepresentation.class, name = "PLANT"),
        @JsonSubTypes.Type(value = HomeDataRepresentation.class, name = "HOME"),
})
public class DeviceGroupRepresentation {
    private DeviceGroupType type;
}
