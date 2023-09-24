package com.mamotec.energycontrolbackend.service.group.home;

import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.device.DeviceType;
import com.mamotec.energycontrolbackend.domain.group.dao.EnergyDataRepresentation;
import com.mamotec.energycontrolbackend.domain.group.dao.home.HomeDataRepresentation;
import com.mamotec.energycontrolbackend.domain.group.dao.home.HomeDeviceGroup;
import com.mamotec.energycontrolbackend.repository.HomeDeviceGroupRepository;
import com.mamotec.energycontrolbackend.service.group.AggregateEnergyDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class HomeAggregateDeviceGroupDataService {

    private final AggregateEnergyDataService energyDataService;
    private final HomeDeviceGroupRepository homeDeviceGroupRepository;

    public HomeDataRepresentation aggregate() {
        Optional<HomeDeviceGroup> home = homeDeviceGroupRepository.findFirstByOrderByCreatedAtDesc();

        if (!home.isPresent() || home.get()
                .getDevices()
                .isEmpty()) {

            return HomeDataRepresentation.builder()
                    .activePower(0)
                    .peakKilowatt(0)
                    .batterySoc(0)
                    .batteryPower(0)
                    .build();
        }

        HomeDeviceGroup homeDeviceGroup = home.get();

        EnergyDataRepresentation rep = energyDataService.aggregate(homeDeviceGroup);

        return HomeDataRepresentation.builder()
                .activePower(rep.getActivePower())
                .peakKilowatt(homeDeviceGroup.getPeakKilowatt())
                .batterySoc(energyDataService.aggregateMeasurement(homeDeviceGroup.getDevicesByType(DeviceType.HYBRID_INVERTER)
                        .stream()
                        .map(Device::getId)
                        .toList(), "batterySoc"))
                .batteryPower(energyDataService.aggregateMeasurement(homeDeviceGroup.getDevicesByType(DeviceType.HYBRID_INVERTER)
                        .stream()
                        .map(Device::getId)
                        .toList(), "batteryPower"))
                .build();
    }
}
