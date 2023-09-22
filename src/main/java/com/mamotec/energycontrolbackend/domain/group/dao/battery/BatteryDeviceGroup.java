package com.mamotec.energycontrolbackend.domain.group.dao.battery;

import com.mamotec.energycontrolbackend.domain.group.DeviceGroup;
import com.mamotec.energycontrolbackend.domain.group.DeviceGroupType;
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
@Table(name = "battery_device_group")
@DiscriminatorValue("BATTERY")
public class BatteryDeviceGroup extends DeviceGroup {

    @Override
    public DeviceGroupType getType() {
        return DeviceGroupType.BATTERY;
    }
}
