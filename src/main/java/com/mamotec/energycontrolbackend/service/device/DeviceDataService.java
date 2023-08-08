package com.mamotec.energycontrolbackend.service.device;

import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.device.DeviceData;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.dao.RegisterMapping;
import com.mamotec.energycontrolbackend.repository.DeviceDataRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeviceDataService {

    private final DeviceDataRepository deviceDataRepository;

    @Transactional
    public void saveDeviceData(Device device, RegisterMapping mapping, String body) {
        DeviceData data = new DeviceData();
        data.setDevice(device);
        data.setValue(body);
        data.setUnit(mapping.getUnit());
        data.setType(mapping.getType());

        deviceDataRepository.save(data);
    }
}
