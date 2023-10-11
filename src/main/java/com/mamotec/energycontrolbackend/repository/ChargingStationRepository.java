package com.mamotec.energycontrolbackend.repository;

import com.mamotec.energycontrolbackend.domain.device.chargingstation.ChargingStationDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChargingStationRepository extends JpaRepository<ChargingStationDevice, Long> {

   Optional<ChargingStationDevice> findFirstByDeviceIdCharger(String deviceIdCharger);

    Optional<ChargingStationDevice> findFirstByUuid(UUID uuid);

}
