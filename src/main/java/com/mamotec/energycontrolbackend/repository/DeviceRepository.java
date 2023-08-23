package com.mamotec.energycontrolbackend.repository;

import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.InterfaceConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Integer> {

    List<Device> findByInterfaceConfigId(long interfaceConfigId);

    boolean existsByUnitIdAndInterfaceConfig(long unitId, InterfaceConfig config);

}
