package com.mamotec.energycontrolbackend.repository;

import com.mamotec.energycontrolbackend.domain.device.SerialDevice;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.InterfaceConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SerialDeviceRepository extends JpaRepository<SerialDevice, Long> {

    boolean existsByUnitIdAndInterfaceConfig(long unitId, InterfaceConfig config);

}
