package com.mamotec.energycontrolbackend.service.device;

import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.device.dao.DeviceCreateResponse;
import com.mamotec.energycontrolbackend.domain.group.DeviceGroup;
import com.mamotec.energycontrolbackend.mapper.DeviceMapper;
import com.mamotec.energycontrolbackend.repository.DeviceGroupRepository;
import com.mamotec.energycontrolbackend.repository.DeviceRepository;
import com.mamotec.energycontrolbackend.service.CrudOperations;
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

    private final DeviceRepository deviceRepository;

    private final DeviceMapper mapper;

    private final InterfaceService interfaceService;

    private final DeviceValidationService validationService;

    private final DeviceGroupRepository deviceGroupRepository;

    @Transactional
    public DeviceCreateResponse create(Device device) {
        validationService.validate(device);
        return mapper.map(save(device));
    }

    public List<Device> getDevicesForInterfaceConfig(long interfaceConfigId) {
        List<Device> byInterfaceConfigId = deviceRepository.findByInterfaceConfigId(interfaceConfigId);
        for (Device device: byInterfaceConfigId) {
            device.setModel(interfaceService.getDeviceNameByManufacturerAndDeviceId(device.getManufacturerId(), device.getDeviceId()));
        }
        return byInterfaceConfigId;
    }

    public List<Device> getAllDevices() {
        List<Device> all = deviceRepository.findAll();

        for (Device device : all) {
            device.setModel(interfaceService.getDeviceNameByManufacturerAndDeviceId(device.getManufacturerId(), device.getDeviceId()));
        }
        return all;
    }

    @Override
    public Optional<JpaRepository<Device, Long>> getRepository() {
        return Optional.of(deviceRepository);
    }


    public List<Device> getValidDevicesForGroup(Long groupId) {
        DeviceGroup deviceGroup = deviceGroupRepository.findById(groupId)
                .orElseThrow();

        List<Device> allByDeviceTypeIn = deviceRepository.findAllByDeviceTypeInAndDeviceGroupNull(deviceGroup.getType()
                .getValidDeviceTypes());

        for (Device device: allByDeviceTypeIn) {
            device.setModel(interfaceService.getDeviceNameByManufacturerAndDeviceId(device.getManufacturerId(), device.getDeviceId()));
        }

        return allByDeviceTypeIn;
    }
}
