package com.mamotec.energycontrolbackend.domain.group;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Table(name = "plant_device_group")
@DiscriminatorValue("PLANT")
public class PlantDeviceGroup extends DeviceGroup {

    @Column(name = "direct_marketing")
    private boolean directMarketing;

    @Override
    public DeviceGroupType getType() {
        return DeviceGroupType.PLANT;
    }
}
