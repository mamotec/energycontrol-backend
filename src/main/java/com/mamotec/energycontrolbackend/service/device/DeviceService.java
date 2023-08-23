package com.mamotec.energycontrolbackend.service.device;

import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.device.dao.DeviceCreateResponse;
import com.mamotec.energycontrolbackend.mapper.DeviceMapper;
import com.mamotec.energycontrolbackend.repository.DeviceRepository;
import com.mamotec.energycontrolbackend.service.CrudOperations;
import com.mamotec.energycontrolbackend.service.interfaceconfig.InterfaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class DeviceService implements CrudOperations<Device> {

    private final DeviceRepository repository;

    private final DeviceMapper mapper;

    private final InterfaceService interfaceService;

    public DeviceCreateResponse create(Device device) {
        return mapper.map(save(device));
    }

    public List<Device> getDevicesForInterfaceConfig(long interfaceConfigId) {
        return repository.findByInterfaceConfigId(interfaceConfigId);
    }

    public List<Device> getAllDevices() {
        List<Device> all = repository.findAll();

        for (Device device : all) {
            device.setModel(interfaceService.getDeviceNameByManufacturerAndDeviceId(device.getManufacturerId(), device.getDeviceId()));
        }
        return all;
    }

    @Override
    public Optional<JpaRepository<Device, Integer>> getRepository() {
        return Optional.of(repository);
    }

}
