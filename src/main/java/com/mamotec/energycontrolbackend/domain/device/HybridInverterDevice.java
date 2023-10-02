package com.mamotec.energycontrolbackend.domain.device;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import static com.mamotec.energycontrolbackend.domain.device.DeviceType.HYBRID_INVERTER;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@AllArgsConstructor
@Table(name = "hybrid_inverter_device")
@DiscriminatorValue("HYBRID_INVERTER")
public class HybridInverterDevice extends Device {

    @Override
    public DeviceType getDeviceType() {
        return HYBRID_INVERTER;
    }
}
