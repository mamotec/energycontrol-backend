package com.mamotec.energycontrolbackend.domain.device;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mamotec.energycontrolbackend.domain.BaseEntity;
import com.mamotec.energycontrolbackend.domain.group.DeviceGroup;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.InterfaceConfig;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;


@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@Table(name = "device")
@SQLDelete(sql = "UPDATE device SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class Device extends BaseEntity {

    // region Fields

    @JoinColumn(name = "interface_config_id")
    @OneToOne
    private InterfaceConfig interfaceConfig;

    private String name;

    /**
     * The manufacturer id in the YAML file.
     */
    @Column(name = "manufacturer_id")
    private long manufacturerId;

    /**
     * The device id in the YAML file.
     */
    @Column(name = "device_id")
    private long deviceId;

    @Enumerated(EnumType.STRING)
    private DeviceType deviceType;

    @NotNull
    private Integer unitId;

    @NotNull
    private boolean active = false;

    @Transient
    private String model;

    @Transient
    private Integer groupId;

    @JsonIgnore
    @ManyToOne
    private DeviceGroup deviceGroup;

    private boolean deleted = Boolean.FALSE;

    // endregion
}
