package com.mamotec.energycontrolbackend.domain.group.dao.heatpump;

import com.mamotec.energycontrolbackend.domain.group.DeviceGroup;
import com.mamotec.energycontrolbackend.domain.group.DeviceGroupType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "heat_pump_device_group")
@DiscriminatorValue("HEAT_PUMP")
public class HeatPumpDeviceGroup extends DeviceGroup {

    @Override
    public DeviceGroupType getType() {
        return DeviceGroupType.HEAT_PUMP;
    }
}
