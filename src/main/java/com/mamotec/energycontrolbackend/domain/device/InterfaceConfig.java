package com.mamotec.energycontrolbackend.domain.device;

import com.mamotec.energycontrolbackend.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@Table(name = "interface_config")
public class InterfaceConfig extends BaseEntity {

    // region Fields

    @Enumerated(EnumType.STRING)
    private DeviceClass deviceClass;

    @OneToMany(mappedBy = "interfaceConfig", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Device> devices = new ArrayList<>();

    // endregion
}
