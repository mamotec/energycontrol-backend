package com.mamotec.energycontrolbackend.service.group;

import com.mamotec.energycontrolbackend.domain.group.DeviceGroup;
import com.mamotec.energycontrolbackend.domain.group.dao.DeviceGroupRepresentation;
import com.mamotec.energycontrolbackend.repository.DeviceGroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AggregateDeviceGroupDataService {

    private final DeviceGroupRepository deviceGroupRepository;
    private final AggregatePlantDataService aggregatePlantDataService;

    public DeviceGroupRepresentation aggregate(long deviceGroupId) {
        DeviceGroup group = deviceGroupRepository.findById(deviceGroupId)
                .orElseThrow();

        switch (group.getType()) {
            case PLANT:
                return aggregatePlantDataService.aggregate(group);
            default:
                return null;
        }
    }
}
