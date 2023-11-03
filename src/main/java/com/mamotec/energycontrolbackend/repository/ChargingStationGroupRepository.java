package com.mamotec.energycontrolbackend.repository;

import com.mamotec.energycontrolbackend.domain.group.dao.chargingstation.ChargingStationDeviceGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChargingStationGroupRepository extends JpaRepository<ChargingStationDeviceGroup, Long> {

    Optional<ChargingStationDeviceGroup> findFirstByOrderByCreatedAtDesc();

}
