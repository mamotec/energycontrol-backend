package com.mamotec.energycontrolbackend.service.group;

import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.group.DeviceGroup;
import com.mamotec.energycontrolbackend.repository.DeviceGroupRepository;
import com.mamotec.energycontrolbackend.repository.DeviceRepository;
import com.mamotec.energycontrolbackend.service.CrudOperations;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.mamotec.energycontrolbackend.service.group.DeviceGroupValidator.validate;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeviceGroupService implements CrudOperations<DeviceGroup> {

    private final DeviceGroupRepository deviceGroupRepository;
    private final DeviceRepository deviceRepository;

    @Override
    public Optional<JpaRepository<DeviceGroup, Long>> getRepository() {
        return Optional.of(deviceGroupRepository);
    }


    public List<DeviceGroup> findAll() {
        return deviceGroupRepository.findAll();
    }

    public DeviceGroup findById(Long id) {
        return deviceGroupRepository.findById(id)
                .orElseThrow();
    }

    public void deleteGroup(Long id) {
        delete(id);
    }

    public void addDevicesToGroup(Long id, List<Device> devices) {
        Optional<DeviceGroup> byId = deviceGroupRepository.findById(id);
        if (byId.isPresent()) {
            DeviceGroup deviceGroup = byId.get();

            for (Device d : devices) {
                Device device = deviceRepository.findById(d.getId())
                        .orElseThrow();
                validate(deviceGroup, device);
                device.setDeviceGroup(deviceGroup);
                deviceRepository.save(device);
            }
        }
    }

    public void deleteDevicesFromGroup(List<Device> devices) {
        for (Device d : devices) {
            Device device = deviceRepository.findById(d.getId())
                    .orElseThrow();
            device.setDeviceGroup(null);
            deviceRepository.save(device);
        }
    }
}
