package com.mamotec.energycontrolbackend.service.group;

import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.device.DeviceType;
import com.mamotec.energycontrolbackend.domain.group.DeviceGroup;
import com.mamotec.energycontrolbackend.domain.group.dao.EnergyDataRepresentation;
import com.mamotec.energycontrolbackend.domain.group.dao.home.BiDirectionalEnergy;
import com.mamotec.energycontrolbackend.service.device.DeviceDataReadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

import static com.mamotec.energycontrolbackend.utils.ConversionUtils.conversionMethodBatteryPower;

@Service
@RequiredArgsConstructor
@Slf4j
public class AggregateEnergyDataService implements AggregateService {

    private final DeviceDataReadService deviceDataReadService;

    @Override
    public EnergyDataRepresentation aggregate(DeviceGroup group) {
        List<Long> deviceIds = group.getDevicesByType(DeviceType.HYBRID_INVERTER)
                .stream()
                .map(Device::getId)
                .toList();


        return EnergyDataRepresentation.builder()
                .activePower(aggregateMeasurement(deviceIds, "power", null) + aggregateMeasurement(deviceIds, "genPower", conversionMethodBatteryPower()))
                .build();
    }

    public long aggregateMeasurement(List<Long> deviceIds, String measurement, Function<Long, Long> conversionMethod) {
        long data = deviceDataReadService.readLastDeviceData(deviceIds, measurement);
        if (conversionMethod != null) {
            data = conversionMethod.apply(data);
        }
        return data;
    }

    public BiDirectionalEnergy aggregateBiMeasurement(List<Long> deviceIds, String measurement, Function<Long, Long> conversionMethod) {
        Long data = deviceDataReadService.readLastDeviceData(deviceIds, measurement);
        BiDirectionalEnergy.BiDirectionalEnergyBuilder builder = BiDirectionalEnergy.builder();


        if (conversionMethod != null) {
            data = conversionMethod.apply(data);
            builder.value(data);
        }
        builder.consumption(data < 0);

        return builder.value(data).build();
    }


}
