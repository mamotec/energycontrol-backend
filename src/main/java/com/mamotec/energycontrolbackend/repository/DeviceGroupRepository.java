package com.mamotec.energycontrolbackend.repository;

import com.mamotec.energycontrolbackend.domain.group.DeviceGroup;
import com.mamotec.energycontrolbackend.domain.group.DeviceGroupType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeviceGroupRepository extends JpaRepository<DeviceGroup, Long> {

    Optional<DeviceGroup> findByType(DeviceGroupType type);
}
