package com.mamotec.energycontrolbackend.service.device;

import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.device.dao.DeviceCreateResponse;
import com.mamotec.energycontrolbackend.mapper.DeviceMapper;
import com.mamotec.energycontrolbackend.repository.DeviceRepository;
import com.mamotec.energycontrolbackend.service.CrudOperations;
import com.mamotec.energycontrolbackend.service.influx.InfluxService;
import com.mamotec.energycontrolbackend.service.interfaceconfig.InterfaceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
@Validated
public class DeviceService implements CrudOperations<Device> {

    private final DeviceRepository repository;

    private final DeviceMapper mapper;

    private final InterfaceService interfaceService;

    private final InfluxService influxService;

    private final DeviceValidationService validationService;

    @Transactional
    public DeviceCreateResponse create(Device device) {
        validationService.validate(device);
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

    @Transactional
    public void deleteDevice(long id) {
        Optional<Device> byId = repository.findById(Math.toIntExact(id));

        // Delete Data Points from InfluxDB
        if (byId.isPresent()) {
            Device device = byId.get();
            influxService.deleteAllDataPointsForDevice(device);
        }

        // Delete Device in DB
        delete(Math.toIntExact(id));
    }

    @Override
    public Optional<JpaRepository<Device, Integer>> getRepository() {
        return Optional.of(repository);
    }


}
