package com.mamotec.energycontrolbackend.service.device.plant;

import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.device.DeviceType;
import com.mamotec.energycontrolbackend.domain.device.dao.DeviceCreateRequest;
import com.mamotec.energycontrolbackend.domain.device.dao.DeviceTypeResponse;
import com.mamotec.energycontrolbackend.domain.group.DeviceGroup;
import com.mamotec.energycontrolbackend.mapper.DeviceMapper;
import com.mamotec.energycontrolbackend.repository.DeviceGroupRepository;
import com.mamotec.energycontrolbackend.repository.DeviceRepository;
import com.mamotec.energycontrolbackend.service.CrudOperations;
import com.mamotec.energycontrolbackend.service.device.DeviceService;
import com.mamotec.energycontrolbackend.service.device.DeviceValidationService;
import com.mamotec.energycontrolbackend.service.interfaceconfig.InterfaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
@Validated
public class PlantDeviceService implements CrudOperations<Device>, DeviceService {

    private final DeviceRepository deviceRepository;

    private final InterfaceService interfaceService;

    private final DeviceValidationService validationService;

    private final DeviceGroupRepository deviceGroupRepository;

    private final DeviceMapper deviceMapper;

    @Override
    public Device findById(Long id) {
        Device device = deviceRepository.findById(id)
                .orElseThrow();

        device.setModel(interfaceService.getDeviceNameByManufacturerAndDeviceId(device));

        return device;
    }


    public List<Device> getDevicesForInterfaceConfig(long interfaceConfigId) {
        List<Device> byInterfaceConfigId = deviceRepository.findByInterfaceConfigId(interfaceConfigId);
        for (Device device : byInterfaceConfigId) {
            device.setModel(interfaceService.getDeviceNameByManufacturerAndDeviceId(device));
        }
        return byInterfaceConfigId;
    }

    public List<Device> getAllDevices() {
        List<Device> all = deviceRepository.findAll();

        for (Device device : all) {
            device.setModel(interfaceService.getDeviceNameByManufacturerAndDeviceId(device));
        }
        return all;
    }

    public List<Device> getValidDevicesForGroup(Long groupId) {
        DeviceGroup deviceGroup = deviceGroupRepository.findById(groupId)
                .orElseThrow();

        List<Device> allByDeviceTypeIn = deviceRepository.findAllByDeviceTypeInAndDeviceGroupNull(deviceGroup.getType()
                .getValidDeviceTypes());

        for (Device device : allByDeviceTypeIn) {
            device.setModel(interfaceService.getDeviceNameByManufacturerAndDeviceId(device));
        }

        return allByDeviceTypeIn;
    }


    @Override
    public Optional<JpaRepository<Device, Long>> getRepository() {
        return Optional.of(deviceRepository);
    }

    @Override
    public Device create(DeviceCreateRequest request) {
        Device device = deviceMapper.map(request);
        validationService.validate(device);

        return save(device);
    }

    @Override
    public void delete(Long id) {
        deviceRepository.deleteById(id);
    }

    @Override
    public List<DeviceTypeResponse> getAllDeviceTypes() {
        DeviceTypeResponse hybridInverter = new DeviceTypeResponse(DeviceType.HYBRID_INVERTER, "Hybrid Wechselrichter");
        DeviceTypeResponse chargingStation = new DeviceTypeResponse(DeviceType.CHARGING_STATION, "Ladestation");
        DeviceTypeResponse heatPump = new DeviceTypeResponse(DeviceType.HEAT_PUMP, "Wärmepumpe");
        DeviceTypeResponse battery = new DeviceTypeResponse(DeviceType.BATTERY, "Batterie");

        return new ArrayList<>(List.of(hybridInverter, chargingStation, heatPump, battery));
    }
}
