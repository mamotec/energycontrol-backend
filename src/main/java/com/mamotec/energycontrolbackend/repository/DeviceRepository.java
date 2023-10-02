package com.mamotec.energycontrolbackend.repository;

import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.device.DeviceType;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.InterfaceConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

    List<Device> findByInterfaceConfigId(long interfaceConfigId);

    @Modifying
    @Query("update Device d set d.active = ?2 where d.id = ?1")
    void markDeviceAsActive(long id, boolean active);

    List<Device> findAllByDeviceTypeInAndDeviceGroupNull(List<DeviceType> validDeviceTypes);

    boolean existsByUnitIdAndInterfaceConfig(long unitId, InterfaceConfig config);

    boolean existsByDeviceType(DeviceType deviceType);

}
