package com.mamotec.energycontrolbackend.cron;


import com.mamotec.energycontrolbackend.domain.device.chargingstation.ChargingStationDevice;
import com.mamotec.energycontrolbackend.repository.ChargingStationRepository;
import com.mamotec.energycontrolbackend.service.device.ChargingStationEnergyDistributionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChargingStationEnergyDistributionScheduler {

    private final ChargingStationRepository chargingStationRepository;
    private final ChargingStationEnergyDistributionService distributionService;

    @Scheduled(cron = "*/3 * * * * *")
    @Transactional
    public void run() {
        List<ChargingStationDevice> stationDeviceList = chargingStationRepository.findAllByActiveIsTrue();

        for (ChargingStationDevice chargingStationDevice : stationDeviceList) {
            switch (chargingStationDevice.getEnergyDistributionEvent()) {
                case RENEWABLE_ENERGY -> distributionService.renewableEnergyDistribution(chargingStationDevice);
                case UNMANAGED -> distributionService.unmanagedEnergyDistribution(chargingStationDevice);
            }
        }



    }
}
