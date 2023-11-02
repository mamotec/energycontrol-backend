package com.mamotec.energycontrolbackend.service.group.home;

import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.device.DeviceType;
import com.mamotec.energycontrolbackend.domain.group.dao.EnergyDataRepresentation;
import com.mamotec.energycontrolbackend.domain.group.dao.home.BiDirectionalEnergy;
import com.mamotec.energycontrolbackend.domain.group.dao.home.HomeDataRepresentation;
import com.mamotec.energycontrolbackend.domain.group.dao.home.HomeDeviceGroup;
import com.mamotec.energycontrolbackend.repository.HomeDeviceGroupRepository;
import com.mamotec.energycontrolbackend.service.group.AggregateEnergyDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.mamotec.energycontrolbackend.utils.ConversionUtils.conversionMethodBatteryPower;

@Service
@RequiredArgsConstructor
@Slf4j
public class HomeAggregateDeviceGroupDataService {

    private final AggregateEnergyDataService energyDataService;
    private final HomeDeviceGroupRepository homeDeviceGroupRepository;

    public HomeDataRepresentation aggregate() {
        Optional<HomeDeviceGroup> home = homeDeviceGroupRepository.findFirstByOrderByCreatedAtDesc();

        if (home.isEmpty() || home.get()
                .getDevices()
                .isEmpty()) {

            return HomeDataRepresentation.builder()
                    .activePower(0)
                    .peakKilowatt(home.map(HomeDeviceGroup::getPeakKilowatt)
                            .orElse(0L))
                    .batterySoc(0)
                    .batteryPower(BiDirectionalEnergy.builder()
                            .consumption(false)
                            .value(0)
                            .build())
                    .grid(BiDirectionalEnergy.builder()
                            .consumption(false)
                            .value(0)
                            .build())
                    .houseHoldPower(0)
                    .chargingStation(BiDirectionalEnergy.builder()
                            .consumption(false)
                            .value(0)
                            .build())
                    .heatPumpActive(false)
                    .build();
        }

        HomeDeviceGroup homeDeviceGroup = home.get();

        EnergyDataRepresentation rep = energyDataService.aggregate(homeDeviceGroup);

        List<Long> deviceIds = homeDeviceGroup.getDevicesByType(DeviceType.HYBRID_INVERTER)
                .stream()
                .map(Device::getId)
                .toList();

        HomeDataRepresentation.HomeDataRepresentationBuilder homeDataRepresentationBuilder = HomeDataRepresentation.builder()
                .activePower(rep.getActivePower())
                .peakKilowatt(homeDeviceGroup.getPeakKilowatt())
                .heatPumpActive(true)
                .chargingStation(energyDataService.aggregateBiMeasurement(deviceIds, "currentImport", null))
                .grid(energyDataService.aggregateBiMeasurement(deviceIds, "gridPower", conversionMethodBatteryPower()))
                .batterySoc(energyDataService.aggregateMeasurement(deviceIds, "batterySoc", null))
                .batteryPower(energyDataService.aggregateBiMeasurement(deviceIds, "batteryPower", conversionMethodBatteryPower()));

        homeDataRepresentationBuilder.houseHoldPower((homeDataRepresentationBuilder.build().getActivePower()
                + homeDataRepresentationBuilder.build().getBatteryPower().getValue()
                + homeDataRepresentationBuilder.build().getGrid().getValue()) - homeDataRepresentationBuilder.build().getChargingStation().getValue());
        return homeDataRepresentationBuilder.build();
    }
}
