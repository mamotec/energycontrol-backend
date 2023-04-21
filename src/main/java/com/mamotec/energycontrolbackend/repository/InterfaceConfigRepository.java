package com.mamotec.energycontrolbackend.repository;

import com.mamotec.energycontrolbackend.domain.device.InterfaceConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterfaceConfigRepository extends JpaRepository<InterfaceConfig, Integer> {
}
