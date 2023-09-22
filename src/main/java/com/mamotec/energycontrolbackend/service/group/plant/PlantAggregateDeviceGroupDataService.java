package com.mamotec.energycontrolbackend.service.group.plant;

import com.mamotec.energycontrolbackend.domain.group.DeviceGroup;
import com.mamotec.energycontrolbackend.domain.group.dao.DeviceGroupRepresentation;
import com.mamotec.energycontrolbackend.domain.group.dao.plant.PlantDataRepresentation;
import com.mamotec.energycontrolbackend.repository.DeviceGroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlantAggregateDeviceGroupDataService {

    public PlantDataRepresentation aggregate(DeviceGroup group) {

        return null;
    }
}
