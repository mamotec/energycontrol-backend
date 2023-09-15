package com.mamotec.energycontrolbackend.service.group;

import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.device.SerialDevice;
import com.mamotec.energycontrolbackend.domain.device.dao.DeviceLinkRequest;
import com.mamotec.energycontrolbackend.domain.group.DeviceGroup;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.InterfaceType;
import com.mamotec.energycontrolbackend.repository.DeviceGroupRepository;
import com.mamotec.energycontrolbackend.repository.DeviceRepository;
import com.mamotec.energycontrolbackend.service.CrudOperations;
import com.mamotec.energycontrolbackend.service.interfaceconfig.InterfaceService;
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
    private final InterfaceService interfaceService;

    @Override
    public DeviceGroup save(DeviceGroup deviceGroup) {
        return deviceGroupRepository.save(deviceGroup);
    }

    public List<DeviceGroup> findAll() {
        List<DeviceGroup> all = deviceGroupRepository.findAll();

        for (DeviceGroup g : all) {
            for (Device d : g.getDevices()) {
                if (d.getInterfaceConfig().getType().equals(InterfaceType.RS485)) {
                    SerialDevice serialDevice = (SerialDevice) d;
                    serialDevice.setModel(interfaceService.getDeviceNameByManufacturerAndDeviceId(serialDevice.getManufacturerId(), serialDevice.getDeviceId()));
                }
            }
        }
        return all;
    }

    public DeviceGroup findById(Long id) {
        return deviceGroupRepository.findById(id)
                .orElseThrow();
    }

    public void deleteGroup(Long id) {
        DeviceGroup group = findById(id);
        for (Device d : group.getDevices()) {
            d.setDeviceGroup(null);
            deviceRepository.save(d);
        }
        delete(id);
    }

    public void addDevicesToGroup(Long id, DeviceLinkRequest request) {
        Optional<DeviceGroup> byId = deviceGroupRepository.findById(id);
        if (byId.isPresent()) {
            DeviceGroup deviceGroup = byId.get();

            for (Long deviceIdToLink : request.getDeviceIds()) {
                Device device = deviceRepository.findById(deviceIdToLink)
                        .orElseThrow();
                validate(deviceGroup, device);
                device.setDeviceGroup(deviceGroup);
                deviceRepository.save(device);
            }
        }
    }

    public void deleteDevicesFromGroup(DeviceLinkRequest request) {
        for (Long deviceIdToUnlink : request.getDeviceIds()) {
            Device device = deviceRepository.findById(deviceIdToUnlink)
                    .orElseThrow();
            device.setDeviceGroup(null);
            deviceRepository.save(device);
        }
    }

    @Override
    public Optional<JpaRepository<DeviceGroup, Long>> getRepository() {
        return Optional.of(deviceGroupRepository);
    }

}
