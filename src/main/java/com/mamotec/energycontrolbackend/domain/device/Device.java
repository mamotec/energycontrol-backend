package com.mamotec.energycontrolbackend.domain.device;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.mamotec.energycontrolbackend.domain.BaseEntity;
import com.mamotec.energycontrolbackend.domain.group.DeviceGroup;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.InterfaceConfig;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;


@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Table(name = "device")
@SQLDelete(sql = "UPDATE device SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "device_type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = HybridInverterDevice.class, name = "HYBRID_INVERTER"),
        @JsonSubTypes.Type(value = ChargingStationDevice.class, name = "CHARGING_STATION"),
})
public abstract class Device extends BaseEntity {

    // region Fields
    private String name;

    @JoinColumn(name = "interface_config_id")
    @OneToOne
    private InterfaceConfig interfaceConfig;


    @NotNull
    @JsonProperty(required = true)
    private boolean active = false;

    @Transient
    private Integer groupId;

    @JsonIgnore
    @ManyToOne
    private DeviceGroup deviceGroup;

    @Transient
    private String model;

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

    private boolean deleted = Boolean.FALSE;

    private Integer unitId = null;

    private String host;
    private String port;

    private int priority;

    public abstract DeviceType getDeviceType();

    // endregion
}
