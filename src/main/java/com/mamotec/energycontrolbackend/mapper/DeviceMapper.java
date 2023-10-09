package com.mamotec.energycontrolbackend.mapper;

import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.device.HybridInverterDevice;
import com.mamotec.energycontrolbackend.domain.device.chargingstation.ChargingStationDevice;
import com.mamotec.energycontrolbackend.domain.device.dao.*;
import org.mapstruct.Mapper;
import org.mapstruct.SubclassExhaustiveStrategy;
import org.mapstruct.SubclassMapping;

import java.util.Objects;

@Mapper(componentModel = "spring", subclassExhaustiveStrategy = SubclassExhaustiveStrategy.RUNTIME_EXCEPTION)
public interface DeviceMapper {

    @SubclassMapping(source = HybridInverterCreateRequest.class, target = HybridInverterDevice.class)
    @SubclassMapping(source = ChargingStationCreateRequest.class, target = ChargingStationDevice.class)
    Device map(DeviceCreateRequest request);

    DeviceCreateResponse map(Device device);

    default void update(DeviceUpdateRequest request, final Device device) {
        if (!Objects.isNull(request.getName())) {
            device.setName(request.getName());
        }
        if (!Objects.isNull(request.getPriority())){
            device.setPriority(request.getPriority());
        }
        if (!Objects.isNull(request.getEnergyDistributionEvent())) {
            device.setEnergyDistributionEvent(request.getEnergyDistributionEvent());
        }
    }

}
