package com.mamotec.energycontrolbackend.mapper;

import com.mamotec.energycontrolbackend.domain.device.ChargingStationDevice;
import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.device.HybridInverterDevice;
import com.mamotec.energycontrolbackend.domain.device.dao.*;
import org.mapstruct.Mapper;
import org.mapstruct.SubclassExhaustiveStrategy;
import org.mapstruct.SubclassMapping;

@Mapper(componentModel = "spring", subclassExhaustiveStrategy = SubclassExhaustiveStrategy.RUNTIME_EXCEPTION)
public interface DeviceMapper {

    @SubclassMapping(source = HybridInverterDeviceCreateRequest.class, target = HybridInverterDevice.class)
    @SubclassMapping(source = ChargingStationDeviceCreateRequest.class, target = ChargingStationDevice.class)
    Device map(DeviceCreateRequest request);

    DeviceCreateResponse map(Device device);

}
