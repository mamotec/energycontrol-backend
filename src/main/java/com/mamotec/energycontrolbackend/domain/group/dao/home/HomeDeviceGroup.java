package com.mamotec.energycontrolbackend.domain.group.dao.home;

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
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Table(name = "home_device_group")
@DiscriminatorValue("HOME")
public class HomeDeviceGroup extends DeviceGroup {

    @Column(name = "peak_kilowatt")
    private long peakKilowatt;

    @Override
    public DeviceGroupType getType() {
        return DeviceGroupType.HOME;
    }
}
