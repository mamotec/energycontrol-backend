package com.mamotec.energycontrolbackend.domain.group;

import com.mamotec.energycontrolbackend.domain.device.DeviceType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

import static com.mamotec.energycontrolbackend.domain.device.DeviceType.*;

@Schema(description = "Die Gruppen Typen")
@Getter
public enum DeviceGroupType {

    PLANT(INVERTER, HYBRID_INVERTER),
    CHARGING_STATION(DeviceType.CHARGING_STATION),
    HEAT_PUMP(DeviceType.HEAT_PUMP),
    BATTERY(DeviceType.BATTERY),
    HOME(HYBRID_INVERTER);

    private final List<DeviceType> validDeviceTypes;

    DeviceGroupType(DeviceType... types) {
        validDeviceTypes = Arrays.asList(types);
    }

    public String getDeviceGroupType(DeviceGroupType type) {
        return type.toString();
    }

    public boolean canAddDeviceToGroup(DeviceType type) {
        return validDeviceTypes.contains(type);
    }

}
