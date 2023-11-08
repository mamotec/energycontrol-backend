package com.mamotec.energycontrolbackend.cron;


import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.device.HybridInverterDevice;
import com.mamotec.energycontrolbackend.domain.device.chargingstation.ChargingStationDevice;
import com.mamotec.energycontrolbackend.repository.ChargingStationRepository;
import com.mamotec.energycontrolbackend.repository.HybridInverterRepository;
import com.mamotec.energycontrolbackend.service.device.chargingStation.ChargingStationEnergyDistributionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class EnergyDistributionScheduler {

    private final ChargingStationEnergyDistributionService distributionService;
    private final ChargingStationRepository chargingStationRepository;
    private final HybridInverterRepository hybridInverterRepository;

    @Scheduled(cron = "*/1 * * * * *")
    public void run() {
        Optional<ChargingStationDevice> chargingDevice = chargingStationRepository.findFirstByActiveIsTrue();
        Optional<HybridInverterDevice> inverterDevice = hybridInverterRepository.findFirstByActiveIsTrue();

        if (inverterDevice.isPresent() && chargingDevice.isPresent()) {
            ChargingStationDevice chargingStationDevice = chargingDevice.get();
            HybridInverterDevice hybridInverterDevice = inverterDevice.get();

            Comparator<Device> priorityComparator = Comparator.comparing(Device::getPriority);

            distributeEnergy(chargingStationDevice, priorityComparator.compare(chargingStationDevice, hybridInverterDevice) > 0);
        }
    }

    private void distributeEnergy(ChargingStationDevice device, boolean higherPriorityThanBattery) {
        switch (device.getEnergyDistributionEvent()) {
            case RENEWABLE_ENERGY -> distributionService.renewableEnergyDistribution(device, higherPriorityThanBattery);
            case MANAGED -> distributionService.managedEnergyDistribution(device);
            case UNMANAGED -> distributionService.unmanagedEnergyDistribution(device);
        }
    }

}
