package com.mamotec.energycontrolbackend.repository;

import com.mamotec.energycontrolbackend.domain.group.dao.home.HomeDeviceGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HomeDeviceGroupRepository extends JpaRepository<HomeDeviceGroup, Long> {

    Optional<HomeDeviceGroup> findFirstByOrderByCreatedAtDesc();

}
