package com.mamotec.energycontrolbackend.domain.device;

import com.mamotec.energycontrolbackend.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;


@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@Table(name = "device")
public class Device extends BaseEntity {

    // region Fields

    private String name;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interface_config_id")
    private InterfaceConfig interfaceConfig;

    private String serialNumber;

    @OneToMany(mappedBy = "device", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DeviceData> deviceData;

    // endregion
}
