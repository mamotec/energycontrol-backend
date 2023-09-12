package com.mamotec.energycontrolbackend.service.group;

import com.mamotec.energycontrolbackend.domain.group.DeviceGroup;
import com.mamotec.energycontrolbackend.domain.group.dao.DeviceGroupRepresentation;

public interface AggregateService {

    DeviceGroupRepresentation aggregate(DeviceGroup deviceGroup);
}
