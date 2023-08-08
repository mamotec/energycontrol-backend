package com.mamotec.energycontrolbackend.domain.device;

import com.mamotec.energycontrolbackend.domain.BaseEntity;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.dao.Unit;
import jakarta.persistence.*;
import lombok.*;

import java.lang.reflect.Type;
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

    private String value;

    @Enumerated(EnumType.STRING)
    private Unit unit;

    /**
     * The type of the value. (power, voltage, current, etc.
     */
    private String type;

    // endregion

}
