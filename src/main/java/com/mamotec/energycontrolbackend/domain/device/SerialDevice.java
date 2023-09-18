package com.mamotec.energycontrolbackend.domain.device;

import com.mamotec.energycontrolbackend.domain.interfaceconfig.InterfaceType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor(force = true)
@Table(name = "serial_device")
@DiscriminatorValue("RS485")
public class SerialDevice extends Device {

    @Override
    public InterfaceType getInterfaceType() {
        return InterfaceType.RS485;
    }

}
