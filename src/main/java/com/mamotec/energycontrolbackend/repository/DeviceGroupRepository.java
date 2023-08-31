package com.mamotec.energycontrolbackend.repository;

import com.mamotec.energycontrolbackend.domain.group.DeviceGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceGroupRepository extends JpaRepository<DeviceGroup, Integer> {
}
