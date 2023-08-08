package com.mamotec.energycontrolbackend.domain.interfaceconfig;


import com.mamotec.energycontrolbackend.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@Table(name = "interface_config")
public class InterfaceConfig extends BaseEntity {

    /**
     * ID specified in YAML file for the given device internally
     */
    @Column(name = "protocol_id")
    private long protocolID;

    @Enumerated(EnumType.STRING)
    private InterfaceType type;

    private String port;

}
