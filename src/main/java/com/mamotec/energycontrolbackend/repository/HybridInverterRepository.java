package com.mamotec.energycontrolbackend.repository;

import com.mamotec.energycontrolbackend.domain.device.HybridInverterDevice;
import com.mamotec.energycontrolbackend.domain.device.chargingstation.ChargingStationDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface HybridInverterRepository extends JpaRepository<HybridInverterDevice, Long> {

    Optional<HybridInverterDevice> findFirstByActiveIsTrue();
}
