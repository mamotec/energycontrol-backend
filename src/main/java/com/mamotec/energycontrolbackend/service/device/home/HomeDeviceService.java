package com.mamotec.energycontrolbackend.service.device.home;

import com.mamotec.energycontrolbackend.domain.device.EnergyDistributionEvent;
import com.mamotec.energycontrolbackend.domain.device.chargingstation.ChargingStationDevice;
import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.device.DeviceType;
import com.mamotec.energycontrolbackend.domain.device.HybridInverterDevice;
import com.mamotec.energycontrolbackend.domain.device.dao.DeviceCreateRequest;
import com.mamotec.energycontrolbackend.domain.device.dao.DeviceTypeResponse;
import com.mamotec.energycontrolbackend.domain.device.dao.DeviceUpdateRequest;
import com.mamotec.energycontrolbackend.domain.device.dao.EnergyDistributionResponse;
import com.mamotec.energycontrolbackend.domain.group.DeviceGroup;
import com.mamotec.energycontrolbackend.domain.group.DeviceGroupType;
import com.mamotec.energycontrolbackend.domain.group.dao.home.HomeDeviceGroup;
import com.mamotec.energycontrolbackend.domain.group.dao.plant.PlantDeviceGroup;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.InterfaceConfig;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.InterfaceType;
import com.mamotec.energycontrolbackend.mapper.DeviceMapper;
import com.mamotec.energycontrolbackend.repository.DeviceGroupRepository;
import com.mamotec.energycontrolbackend.repository.DeviceRepository;
import com.mamotec.energycontrolbackend.repository.InterfaceConfigRepository;
import com.mamotec.energycontrolbackend.service.CrudOperations;
import com.mamotec.energycontrolbackend.service.device.DeviceGroupFactory;
import com.mamotec.energycontrolbackend.service.device.DeviceService;
import com.mamotec.energycontrolbackend.service.device.DeviceValidationService;
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
public class HomeDeviceService implements CrudOperations<Device>, DeviceService {

    private final DeviceRepository deviceRepository;

    private final DeviceValidationService validationService;

    private final DeviceGroupRepository deviceGroupRepository;

    private final DeviceMapper deviceMapper;

    private final InterfaceConfigRepository interfaceConfigRepository;


    @Override
    public Optional<JpaRepository<Device, Long>> getRepository() {
        return Optional.of(deviceRepository);
    }

    @Override
    @Transactional
    public Device create(DeviceCreateRequest request) {
        InterfaceConfig c = createInterfaceConfig(request);

        Device device = deviceMapper.map(request);
        device.setPriority(Objects.isNull(deviceRepository.findDeviceWithBiggestPriority()) ? 0 : deviceRepository.findDeviceWithBiggestPriority() + 1);
        device.setInterfaceConfig(c);
        validationService.validate(device);
        Device saved = save(device);

        // Erstelle Gruppe für Gerät.
        createDeviceGroup(request, saved);

        return saved;
    }

    @Override
    public Device update(Long id, DeviceUpdateRequest request) {
        Device d = deviceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Device with id " + id + " not found."));

        if (request.getPriority() != d.getPriority()) {
            doPriorityUpdate(request, d);
        }

        deviceMapper.update(request, d);
        return save(d);
    }

    private void doPriorityUpdate(DeviceUpdateRequest request, Device d) {
        Device deviceToAdjust = deviceRepository.findFirstByPriority(request.getPriority());
        if (request.getPriority() > d.getPriority()) {
            deviceToAdjust.setPriority(deviceToAdjust.getPriority() - 1);
        } else if (request.getPriority() < d.getPriority()) {
            deviceToAdjust.setPriority(deviceToAdjust.getPriority() + 1);
        }

        save(deviceToAdjust);
    }

    private InterfaceConfig createInterfaceConfig(DeviceCreateRequest request) {
        // Check if there is a TCP interface
        Optional<InterfaceConfig> byType = interfaceConfigRepository.findByType(InterfaceType.TCP);
        if (byType.isEmpty()) {
            InterfaceConfig config = new InterfaceConfig();
            config.setType(InterfaceType.TCP);
            config.setDescription("TCP Schnittstelle");
            return interfaceConfigRepository.save(config);
        } else {
            return byType.get();
        }

    }

    private void createDeviceGroup(DeviceCreateRequest request, Device saved) {

        DeviceGroup deviceGroup = DeviceGroupFactory.getDeviceGroup(this.getDeviceGroupForType(saved));

        if (deviceGroup instanceof HomeDeviceGroup group) {
            group.setPeakKilowatt(request.getPeakKilowatt());
        } else if (deviceGroup instanceof PlantDeviceGroup group) {
            group.setPeakKilowatt(request.getPeakKilowatt());
        }

        deviceGroup.setName(saved.getName());
        deviceGroup.setDevices(List.of(saved));
        deviceGroupRepository.save(deviceGroup);

        saved.setDeviceGroup(deviceGroup);
        save(saved);
    }

    private DeviceGroupType getDeviceGroupForType(Device device) {
        if (device instanceof HybridInverterDevice) {
            return DeviceType.HYBRID_INVERTER.getGroup();
        } else if (device instanceof ChargingStationDevice) {
            return DeviceType.CHARGING_STATION.getGroup();
        } else {
            throw new IllegalArgumentException("Device type not supported.");
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Device d = deviceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Device with id " + id + " not found."));
        d.setInterfaceConfig(null);
        deviceRepository.save(d);

        if (Objects.isNull(d.getDeviceGroup())) {
            deviceRepository.deleteById(id);
        } else {
            deviceGroupRepository.deleteById(d.getDeviceGroup()
                    .getId());
        }

    }

    @Override
    @Transactional
    public List<DeviceTypeResponse> getAllDeviceTypes() {
        DeviceTypeResponse hybridInverter = new DeviceTypeResponse(DeviceType.HYBRID_INVERTER, "Hybrid Wechselrichter");
        DeviceTypeResponse chargingStation = new DeviceTypeResponse(DeviceType.CHARGING_STATION, "Ladestation");
        return new ArrayList<>(List.of(hybridInverter, chargingStation));

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
