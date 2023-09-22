package com.mamotec.energycontrolbackend.service.group.home;

import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.device.DeviceType;
import com.mamotec.energycontrolbackend.domain.group.DeviceGroup;
import com.mamotec.energycontrolbackend.domain.group.dao.EnergyDataRepresentation;
import com.mamotec.energycontrolbackend.domain.group.dao.home.HomeDataRepresentation;
import com.mamotec.energycontrolbackend.domain.group.dao.home.HomeDeviceGroup;
import com.mamotec.energycontrolbackend.service.group.AggregateEnergyDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class HomeAggregateDeviceGroupDataService {

    private final AggregateEnergyDataService energyDataService;

    public HomeDataRepresentation aggregate(DeviceGroup group) {
        EnergyDataRepresentation rep = energyDataService.aggregate(group);

        HomeDeviceGroup homeGroup = (HomeDeviceGroup) group;

        return HomeDataRepresentation.builder()
                .activePower(rep.getActivePower())
                .peakKilowatt(homeGroup.getPeakKilowatt())
                .batterySoc(energyDataService.aggregateMeasurement(homeGroup.getDevicesByType(DeviceType.HYBRID_INVERTER)
                        .stream()
                        .map(Device::getId)
                        .toList(), "batterySoc"))
                .batteryPower(energyDataService.aggregateMeasurement(homeGroup.getDevicesByType(DeviceType.HYBRID_INVERTER)
                        .stream()
                        .map(Device::getId)
                        .toList(), "batteryPower"))
                .build();
    }
}
