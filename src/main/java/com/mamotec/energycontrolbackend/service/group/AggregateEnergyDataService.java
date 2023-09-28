package com.mamotec.energycontrolbackend.service.group;

import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.device.DeviceType;
import com.mamotec.energycontrolbackend.domain.group.DeviceGroup;
import com.mamotec.energycontrolbackend.domain.group.dao.EnergyDataRepresentation;
import com.mamotec.energycontrolbackend.service.device.DeviceDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Slf4j
public class AggregateEnergyDataService implements AggregateService {

    private final DeviceDataService deviceDataService;

    @Override
    public EnergyDataRepresentation aggregate(DeviceGroup group) {
        List<Long> deviceIds = group.getDevicesByType(DeviceType.HYBRID_INVERTER)
                .stream()
                .map(Device::getId)
                .toList();


        return EnergyDataRepresentation.builder()
                .activePower(aggregateMeasurement(deviceIds, "power", null))
                .build();
    }

    public long aggregateMeasurement(List<Long> deviceIds, String measurement, Function<Long, Long> conversionMethod) {
        long data = deviceDataService.readLastDeviceData(deviceIds, measurement);
        if (conversionMethod != null) {
            data = conversionMethod.apply(data);
        }
        return data;

    }


}
