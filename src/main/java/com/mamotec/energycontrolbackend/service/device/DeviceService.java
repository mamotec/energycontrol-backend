package com.mamotec.energycontrolbackend.service.device;

import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.device.dao.DeviceCreateRequest;

public interface DeviceService {

    Device create(DeviceCreateRequest request);

    void delete(Long id);
}
