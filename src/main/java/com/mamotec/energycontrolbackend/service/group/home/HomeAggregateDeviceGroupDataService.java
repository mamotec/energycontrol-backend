package com.mamotec.energycontrolbackend.service.group.home;

import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.device.DeviceType;
import com.mamotec.energycontrolbackend.domain.group.DeviceGroupType;
import com.mamotec.energycontrolbackend.domain.group.dao.EnergyDataRepresentation;
import com.mamotec.energycontrolbackend.domain.group.dao.home.HomeDataRepresentation;
import com.mamotec.energycontrolbackend.domain.group.dao.home.HomeDeviceGroup;
import com.mamotec.energycontrolbackend.repository.DeviceGroupRepository;
import com.mamotec.energycontrolbackend.service.group.AggregateEnergyDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class HomeAggregateDeviceGroupDataService {

    private final AggregateEnergyDataService energyDataService;
    private final DeviceGroupRepository deviceGroupRepository;

    public HomeDataRepresentation aggregate() {
        HomeDeviceGroup home = (HomeDeviceGroup) deviceGroupRepository.findByType(DeviceGroupType.HOME)
                .orElseThrow();

        EnergyDataRepresentation rep = energyDataService.aggregate(home);

        return HomeDataRepresentation.builder()
                .activePower(rep.getActivePower())
                .peakKilowatt(home.getPeakKilowatt())
                .batterySoc(energyDataService.aggregateMeasurement(home.getDevicesByType(DeviceType.HYBRID_INVERTER)
                        .stream()
                        .map(Device::getId)
                        .toList(), "batterySoc"))
                .batteryPower(energyDataService.aggregateMeasurement(home.getDevicesByType(DeviceType.HYBRID_INVERTER)
                        .stream()
                        .map(Device::getId)
                        .toList(), "batteryPower"))
                .build();
    }
}
