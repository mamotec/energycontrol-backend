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
public class DeviceGroupCreate {

    private String name;
    private boolean directMarketing;
    private DeviceGroupType type;
}
