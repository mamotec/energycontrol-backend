package com.mamotec.energycontrolbackend.service.device;

import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.device.DeviceType;
import com.mamotec.energycontrolbackend.domain.device.dao.DeviceCreateRequest;
import com.mamotec.energycontrolbackend.domain.device.dao.DeviceTypeResponse;
import com.mamotec.energycontrolbackend.domain.device.dao.DeviceUpdateRequest;
import com.mamotec.energycontrolbackend.domain.device.dao.EnergyDistributionResponse;
import com.mamotec.energycontrolbackend.domain.group.DeviceGroup;
import com.mamotec.energycontrolbackend.domain.group.DeviceGroupType;
import com.mamotec.energycontrolbackend.domain.group.dao.home.HomeDeviceGroup;
import com.mamotec.energycontrolbackend.domain.group.dao.plant.PlantDeviceGroup;

import java.util.List;

import static com.mamotec.energycontrolbackend.domain.group.DeviceGroupType.HOME;
import static com.mamotec.energycontrolbackend.domain.group.DeviceGroupType.PLANT;

public interface DeviceService {

    Device create(DeviceCreateRequest request);

    Device update(Long id, DeviceUpdateRequest request);

    void delete(Long id);

    List<DeviceTypeResponse> getAllDeviceTypes();

    List<EnergyDistributionResponse> getAllEnergyDistributionEvents(DeviceType deviceType);

    default DeviceGroupType getDeviceGroupTypeByClass(DeviceGroup group) {
        if (group instanceof PlantDeviceGroup) {
            return PLANT;
        } else if (group instanceof HomeDeviceGroup) {
            return HOME;
        } else {
            throw new IllegalArgumentException("Unknown device group type");
        }
    }
}
