package com.mamotec.energycontrolbackend.service.device;

import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.device.dao.DeviceCreateRequest;
import com.mamotec.energycontrolbackend.domain.device.dao.DeviceTypeResponse;

import java.util.List;

public interface DeviceService {

    Device create(DeviceCreateRequest request);

    void delete(Long id);

    List<DeviceTypeResponse> getAllDeviceTypes();
}
