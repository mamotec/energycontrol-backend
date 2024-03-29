package com.mamotec.energycontrolbackend.domain.group.dao.plant;

import com.mamotec.energycontrolbackend.domain.group.DeviceGroup;
import com.mamotec.energycontrolbackend.domain.group.DeviceGroupType;
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

    @Column(name = "feed_in_management")
    private boolean feedInManagement;

    @Column(name = "peak_kilowatt")
    private long peakKilowatt;

    @Override
    public DeviceGroupType getType() {
        return DeviceGroupType.PLANT;
    }
}
