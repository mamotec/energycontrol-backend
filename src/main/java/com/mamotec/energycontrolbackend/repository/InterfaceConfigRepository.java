package com.mamotec.energycontrolbackend.repository;

import com.mamotec.energycontrolbackend.domain.interfaceconfig.InterfaceConfig;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.InterfaceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InterfaceConfigRepository extends JpaRepository<InterfaceConfig, Long> {

    Optional<InterfaceConfig> findByType(InterfaceType type);
}
