package com.mamotec.energycontrolbackend.service.device;

import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.device.DeviceType;
import com.mamotec.energycontrolbackend.domain.device.EnergyDistributionEvent;
import com.mamotec.energycontrolbackend.domain.device.dao.DeviceCreateRequest;
import com.mamotec.energycontrolbackend.domain.device.dao.DeviceTypeResponse;
import com.mamotec.energycontrolbackend.domain.device.dao.DeviceUpdateRequest;
import com.mamotec.energycontrolbackend.domain.device.dao.EnergyDistributionResponse;

import java.util.List;

public interface DeviceService {

    Device create(DeviceCreateRequest request);

    Device update(Long id, DeviceUpdateRequest request);

    void delete(Long id);

    List<DeviceTypeResponse> getAllDeviceTypes();

    List<EnergyDistributionResponse> getAllEnergyDistributionEvents(DeviceType deviceType);
}
