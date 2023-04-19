package com.mamotec.energycontrolbackend.domain.device;

import com.mamotec.energycontrolbackend.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.*;

@Data
@Builder
@Entity
@Table(name = "mamotec_interface")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Interface extends BaseEntity {
    //region Fields
    @Enumerated(EnumType.STRING)
    private DeviceCategory deviceCategory;
    //endregion
}
