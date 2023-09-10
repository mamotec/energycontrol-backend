package com.mamotec.energycontrolbackend.domain.group;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@DiscriminatorValue("PLANT")
public class PlantDeviceGroup extends DeviceGroup {

    @Column(name = "direct_marketing")
    private boolean directMarketing;

}
