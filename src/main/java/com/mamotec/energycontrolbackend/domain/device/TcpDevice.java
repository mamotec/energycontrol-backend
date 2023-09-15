package com.mamotec.energycontrolbackend.domain.device;

import com.mamotec.energycontrolbackend.domain.interfaceconfig.InterfaceType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Table(name = "tcp_device")
@DiscriminatorValue("TCP")
public class TcpDevice extends Device {

    private String host;
    private String port;


    @Override
    public InterfaceType getInterfaceType() {
        return InterfaceType.TCP;
    }
}
