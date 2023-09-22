package com.mamotec.energycontrolbackend.domain.group;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.mamotec.energycontrolbackend.domain.BaseEntity;
import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.group.dao.plant.PlantDeviceGroup;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Table(name = "device_group")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type")
@JsonSubTypes({
        @Type(value = PlantDeviceGroup.class, name = "PlantDeviceGroup"),
})
public abstract class DeviceGroup extends BaseEntity {

    private String name;

    @OneToMany(mappedBy = "deviceGroup", fetch = FetchType.EAGER)
    private List<Device> devices;

    public abstract DeviceGroupType getType();

}
