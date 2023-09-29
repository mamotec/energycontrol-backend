package com.mamotec.energycontrolbackend.service.device.home;

import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.device.DeviceType;
import com.mamotec.energycontrolbackend.domain.device.dao.DeviceCreateRequest;
import com.mamotec.energycontrolbackend.domain.device.dao.DeviceTypeResponse;
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
        createDeviceGroup(request, device, saved);

        return saved;
    }

    private void createDeviceGroup(DeviceCreateRequest request, Device device, Device saved) {
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
    }

    @Override
    public void delete(Long id) {
        Device d = deviceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Device with id " + id + " not found."));

        deviceGroupRepository.deleteById(d.getDeviceGroup().getId());

    }

    @Override
    public List<DeviceTypeResponse> getAllDeviceTypes() {
       DeviceTypeResponse hybridInverter = new DeviceTypeResponse(DeviceType.HYBRID_INVERTER, "Hybrid Wechselrichter");
       DeviceTypeResponse chargingStation = new DeviceTypeResponse(DeviceType.CHARGING_STATION, "Ladestation");
       DeviceTypeResponse heatPump = new DeviceTypeResponse(DeviceType.HEAT_PUMP, "Wärmepumpe");
        return new ArrayList<>(List.of(hybridInverter, chargingStation, heatPump));

    }
}
