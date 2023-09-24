package com.mamotec.energycontrolbackend.service.group;

import com.mamotec.energycontrolbackend.domain.group.DeviceGroup;
import com.mamotec.energycontrolbackend.domain.group.dao.DeviceGroupRepresentation;
import com.mamotec.energycontrolbackend.repository.DeviceGroupRepository;
import com.mamotec.energycontrolbackend.service.group.home.HomeAggregateDeviceGroupDataService;
import com.mamotec.energycontrolbackend.service.group.plant.PlantAggregateDeviceGroupDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AggregateDeviceGroupDataService {

    private final DeviceGroupRepository deviceGroupRepository;
    private final HomeAggregateDeviceGroupDataService homeService;
    private final PlantAggregateDeviceGroupDataService plantService;

    public DeviceGroupRepresentation aggregate(long deviceGroupId) {
        DeviceGroup group = deviceGroupRepository.findById(deviceGroupId)
                .orElseThrow();

        switch (group.getType()) {
            case  HOME:
                return homeService.aggregate();
            case PLANT:
                return plantService.aggregate(group);

        }
        return null;
    }
}
