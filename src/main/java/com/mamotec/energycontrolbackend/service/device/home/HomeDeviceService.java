package com.mamotec.energycontrolbackend.service.device.home;

import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.device.DeviceType;
import com.mamotec.energycontrolbackend.domain.device.dao.DeviceCreateRequest;
import com.mamotec.energycontrolbackend.domain.group.DeviceGroup;
import com.mamotec.energycontrolbackend.domain.group.dao.home.HomeDeviceGroup;
import com.mamotec.energycontrolbackend.domain.group.dao.plant.PlantDeviceGroup;
import com.mamotec.energycontrolbackend.mapper.DeviceMapper;
import com.mamotec.energycontrolbackend.repository.DeviceGroupRepository;
import com.mamotec.energycontrolbackend.repository.DeviceRepository;
import com.mamotec.energycontrolbackend.service.CrudOperations;
import com.mamotec.energycontrolbackend.service.device.DeviceService;
import com.mamotec.energycontrolbackend.service.device.DeviceValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
@Validated
public class HomeDeviceService implements CrudOperations<Device>, DeviceService {

    private final DeviceRepository deviceRepository;

    private final DeviceValidationService validationService;

    private final DeviceGroupRepository deviceGroupRepository;

    private final DeviceMapper deviceMapper;


    @Override
    public Optional<JpaRepository<Device, Long>> getRepository() {
        return Optional.of(deviceRepository);
    }

    @Override
    public Device create(DeviceCreateRequest request) {
        Device device = deviceMapper.map(request);

        validationService.validate(device);
        Device saved = save(device);

        // Erstelle Gruppe für Gerät.
        if (!Objects.isNull(device.getDeviceType()
                .getGroup())) {
            DeviceGroup deviceGroup = DeviceGroupFactory.getDeviceGroup(device.getDeviceType()
                    .getGroup());

            if (deviceGroup instanceof HomeDeviceGroup group) {
                group.setPeakKilowatt(request.getPeakKilowatt());
            } else if (deviceGroup instanceof PlantDeviceGroup group) {
                group.setPeakKilowatt(request.getPeakKilowatt());
            }

            deviceGroup.setName(device.getName());
            deviceGroup.setDevices(List.of(device));
            deviceGroupRepository.save(deviceGroup);

            saved.setDeviceGroup(deviceGroup);
            save(saved);
        }

        return saved;
    }

    @Override
    public void delete(Long id) {
        Device d = deviceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Device with id " + id + " not found."));

        deviceGroupRepository.deleteById(d.getDeviceGroup().getId());

    }

    @Override
    public List<DeviceType> getAllDeviceTypes() {
        List<DeviceType> allowedTypes = new ArrayList<>();

        allowedTypes.add(DeviceType.HYBRID_INVERTER);
        allowedTypes.add(DeviceType.CHARGING_STATION);
        allowedTypes.add(DeviceType.HEAT_PUMP);

        return allowedTypes;
    }
}
