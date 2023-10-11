package com.mamotec.energycontrolbackend.service.device.plant;

import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.device.DeviceType;
import com.mamotec.energycontrolbackend.domain.device.EnergyDistributionEvent;
import com.mamotec.energycontrolbackend.domain.device.dao.DeviceCreateRequest;
import com.mamotec.energycontrolbackend.domain.device.dao.DeviceTypeResponse;
import com.mamotec.energycontrolbackend.domain.device.dao.DeviceUpdateRequest;
import com.mamotec.energycontrolbackend.domain.device.dao.EnergyDistributionResponse;
import com.mamotec.energycontrolbackend.domain.group.DeviceGroup;
import com.mamotec.energycontrolbackend.mapper.DeviceMapper;
import com.mamotec.energycontrolbackend.repository.DeviceGroupRepository;
import com.mamotec.energycontrolbackend.repository.DeviceRepository;
import com.mamotec.energycontrolbackend.service.CrudOperations;
import com.mamotec.energycontrolbackend.service.device.DeviceService;
import com.mamotec.energycontrolbackend.service.device.DeviceValidationService;
import com.mamotec.energycontrolbackend.service.interfaceconfig.InterfaceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.*;


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
        device.setEventName(device.getEnergyDistributionEvent()
                .getName());
        device.setEventDescription(device.getEnergyDistributionEvent()
                .getDescription());
        device.setValidEnergyDistributionEvents(Arrays.stream(EnergyDistributionEvent.values())
                .filter(e -> e.getDeviceTypes()
                        .contains(device.getDeviceType()))
                .map(e -> new EnergyDistributionResponse(e, e.getDescription(), e.getName()))
                .toList());

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
            device.setEventName(device.getEnergyDistributionEvent()
                    .getName());
            device.setEventDescription(device.getEnergyDistributionEvent()
                    .getDescription());
            device.setValidEnergyDistributionEvents(Arrays.stream(EnergyDistributionEvent.values())
                    .filter(e -> e.getDeviceTypes()
                            .contains(device.getDeviceType()))
                    .map(e -> new EnergyDistributionResponse(e, e.getDescription(), e.getName()))
                    .toList());
        }
        // Sort by priority
        all.sort((o1, o2) -> {
            if (o1.getPriority() == o2.getPriority()) {
                return 0;
            }
            return o1.getPriority() < o2.getPriority() ? -1 : 1;
        });
        return all;
    }

    public List<Device> fetchValidDeviceGroups(Long groupId) {
        DeviceGroup deviceGroup = deviceGroupRepository.findById(groupId)
                .orElseThrow();

        List<Device> all = deviceRepository.findAll();

        List<Device> filteredList = all.stream()
                .filter(d -> Objects.isNull(d.getDeviceGroup()))
                .filter(d -> getDeviceGroupTypeByClass(deviceGroup).getValidDeviceTypes()
                        .contains(d.getDeviceType()))
                .toList();


        for (Device device : filteredList) {
            device.setModel(interfaceService.getDeviceNameByManufacturerAndDeviceId(device));
        }

        return filteredList;
    }


    @Override
    public Optional<JpaRepository<Device, Long>> getRepository() {
        return Optional.of(deviceRepository);
    }

    @Override
    @Transactional
    public Device create(DeviceCreateRequest request) {
        Device device = deviceMapper.map(request);
        validationService.validate(device);

        return save(device);
    }

    @Override
    public Device update(Long id, DeviceUpdateRequest request) {
        return null;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        deviceRepository.deleteById(id);
    }

    @Override
    @Transactional
    public List<DeviceTypeResponse> getAllDeviceTypes() {
        DeviceTypeResponse hybridInverter = new DeviceTypeResponse(DeviceType.HYBRID_INVERTER, "Hybrid Wechselrichter");
        DeviceTypeResponse chargingStation = new DeviceTypeResponse(DeviceType.CHARGING_STATION, "Ladestation");
        DeviceTypeResponse heatPump = new DeviceTypeResponse(DeviceType.HEAT_PUMP, "Wärmepumpe");
        DeviceTypeResponse battery = new DeviceTypeResponse(DeviceType.BATTERY, "Batterie");

        return new ArrayList<>(List.of(hybridInverter, chargingStation, heatPump, battery));
    }

    @Override
    public List<EnergyDistributionResponse> getAllEnergyDistributionEvents(DeviceType deviceType) {
        EnergyDistributionEvent[] values = EnergyDistributionEvent.values();
        return Arrays.stream(values)
                .filter(e -> e.getDeviceTypes().contains(deviceType))
                .map(e -> new EnergyDistributionResponse(e, e.getDescription(), e.getName()))
                .toList();
    }
}
