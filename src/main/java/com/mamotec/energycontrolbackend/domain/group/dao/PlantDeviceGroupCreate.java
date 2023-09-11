package com.mamotec.energycontrolbackend.domain.group.dao;

import com.mamotec.energycontrolbackend.domain.group.DeviceGroupType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlantDeviceGroupCreate extends DeviceGroupCreate {

    private boolean directMarketing;
    private DeviceGroupType type = DeviceGroupType.PLANT;

}
