package com.mamotec.energycontrolbackend.domain.group.dao.plant;

import com.mamotec.energycontrolbackend.domain.group.dao.DeviceGroupUpdate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlantDeviceGroupUpdate extends DeviceGroupUpdate {

    private boolean directMarketing;

}

