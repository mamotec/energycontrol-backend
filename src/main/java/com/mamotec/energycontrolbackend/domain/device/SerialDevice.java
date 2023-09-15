package com.mamotec.energycontrolbackend.domain.device;

import com.mamotec.energycontrolbackend.domain.group.DeviceGroupType;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.InterfaceType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Table(name = "serial_device")
@DiscriminatorValue("RS485")
public class SerialDevice extends Device {

    private Integer unitId;

    @Override
    public InterfaceType getInterfaceType() {
        return InterfaceType.RS485;
    }

}
