package com.mamotec.energycontrolbackend.service.group;

import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.group.DeviceGroup;
import com.mamotec.energycontrolbackend.domain.group.dao.EnergyDataRepresentation;
import com.mamotec.energycontrolbackend.service.device.DeviceDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AggregateEnergyDataService implements AggregateService {

    private final DeviceDataService deviceDataService;

    @Override
    public EnergyDataRepresentation aggregate(DeviceGroup group) {
        List<Long> devicesIds = group.getDevices()
                .stream()
                .filter(Device::isActive)
                .filter(d -> group.getType()
                        .getValidDeviceTypes()
                        .contains(d.getDeviceType()))
                .map(Device::getId)
                .toList();


        return EnergyDataRepresentation.builder()
                .activePower(aggregateMeasurement(devicesIds, "power"))
                .build();
    }

    public long aggregateMeasurement(List<Long> deviceIds, String measurement) {
        return deviceDataService.readLastDeviceData(deviceIds, measurement);

    }


}
