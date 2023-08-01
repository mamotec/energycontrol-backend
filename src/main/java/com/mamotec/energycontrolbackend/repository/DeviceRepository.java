package com.mamotec.energycontrolbackend.repository;

import com.mamotec.energycontrolbackend.domain.device.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Integer> {

    public List<Device> findByInterfaceConfigId(long interfaceConfigId);
}
