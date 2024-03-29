package com.mamotec.energycontrolbackend.domain.interfaceconfig;


import com.mamotec.energycontrolbackend.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Table(name = "interface_config")
public class InterfaceConfig extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private InterfaceType type;

    private String description;

}
