package com.mamotec.energycontrolbackend.domain.group.dao.plant;

import com.mamotec.energycontrolbackend.domain.group.dao.DeviceGroupRepresentation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlantDataRepresentation extends DeviceGroupRepresentation {

    private long activePower;
}
