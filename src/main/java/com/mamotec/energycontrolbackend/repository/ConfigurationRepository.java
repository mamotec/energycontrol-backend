package com.mamotec.energycontrolbackend.repository;

import com.mamotec.energycontrolbackend.domain.configuration.SystemConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigurationRepository extends JpaRepository<SystemConfiguration, Long> {
}
