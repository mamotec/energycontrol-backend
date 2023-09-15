package com.mamotec.energycontrolbackend.mapper;

import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.device.SerialDevice;
import com.mamotec.energycontrolbackend.domain.device.TcpDevice;
import com.mamotec.energycontrolbackend.domain.device.dao.DeviceCreateRequest;
import com.mamotec.energycontrolbackend.domain.device.dao.SerialDeviceCreateRequest;
import com.mamotec.energycontrolbackend.domain.device.dao.DeviceCreateResponse;
import com.mamotec.energycontrolbackend.domain.device.dao.TcpDeviceCreateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.SubclassExhaustiveStrategy;
import org.mapstruct.SubclassMapping;

@Mapper(componentModel = "spring", subclassExhaustiveStrategy = SubclassExhaustiveStrategy.RUNTIME_EXCEPTION)
public interface DeviceMapper {

    @SubclassMapping(source = SerialDeviceCreateRequest.class, target = SerialDevice.class)
    @SubclassMapping(source = TcpDeviceCreateRequest.class, target = TcpDevice.class)
    Device map(DeviceCreateRequest request);

    DeviceCreateResponse map(Device device);

}
