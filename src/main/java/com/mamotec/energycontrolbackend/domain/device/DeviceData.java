package com.mamotec.energycontrolbackend.domain.device;

import com.mamotec.energycontrolbackend.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@Table(name = "device_data")
public class DeviceData extends BaseEntity {


    // region Fields

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;

    @Column(name = "power_generated")
    private BigDecimal powerGenerated;

    private BigDecimal voltage;

    private BigDecimal current;

    // endregion

}
