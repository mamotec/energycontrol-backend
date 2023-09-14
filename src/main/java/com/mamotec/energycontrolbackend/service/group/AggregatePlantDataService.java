package com.mamotec.energycontrolbackend.service.group;

import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.group.DeviceGroup;
import com.mamotec.energycontrolbackend.domain.group.PlantDeviceGroup;
import com.mamotec.energycontrolbackend.domain.group.dao.DeviceGroupRepresentation;
import com.mamotec.energycontrolbackend.domain.group.dao.plant.PlantDataRepresentation;
import com.mamotec.energycontrolbackend.service.device.DeviceDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AggregatePlantDataService implements AggregateService {

    private final DeviceDataService deviceDataService;

    @Override
    public DeviceGroupRepresentation aggregate(DeviceGroup group) {
        PlantDeviceGroup pg = (PlantDeviceGroup) group;
        return PlantDataRepresentation.builder()
                .activePower(aggregateActivePower(pg))
                .directMarketing(pg.isDirectMarketing())
                .feedInManagement(pg.isFeedInManagement())
                .build();
    }

    private long aggregateActivePower(DeviceGroup group) {

        List<Long> devicesIds = group.getDevices()
                .stream()
                .map(Device::getId)
                .toList();

        return deviceDataService.readLastDeviceData(devicesIds, "power");

    }


}
