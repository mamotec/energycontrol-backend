package com.mamotec.energycontrolbackend.service.device;

import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.device.DeviceType;
import com.mamotec.energycontrolbackend.domain.device.dao.DeviceCreateRequest;

import java.util.List;

public interface DeviceService {

    Device create(DeviceCreateRequest request);

    void delete(Long id);

    List<DeviceType> getAllDeviceTypes();
}
