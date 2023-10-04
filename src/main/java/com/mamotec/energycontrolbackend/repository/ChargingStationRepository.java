package com.mamotec.energycontrolbackend.repository;

import com.mamotec.energycontrolbackend.domain.device.ChargingStationDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChargingStationRepository extends JpaRepository<ChargingStationDevice, Long> {

   Optional<ChargingStationDevice> findFirstByDeviceIdCharger(long deviceIdCharger);

    Optional<ChargingStationDevice> findFirstByUuid(UUID uuid);

}
