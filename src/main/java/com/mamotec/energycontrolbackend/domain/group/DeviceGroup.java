package com.mamotec.energycontrolbackend.domain.group;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.mamotec.energycontrolbackend.domain.BaseEntity;
import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.device.DeviceType;
import com.mamotec.energycontrolbackend.domain.group.dao.plant.PlantDeviceGroup;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Table(name = "device_group")
@SQLDelete(sql = "UPDATE device_group SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type")
@JsonSubTypes({
        @Type(value = PlantDeviceGroup.class, name = "PlantDeviceGroup"),
})
public abstract class DeviceGroup extends BaseEntity {

    private String name;

    @OneToMany(mappedBy = "deviceGroup", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Device> devices;

    private boolean deleted = Boolean.FALSE;


    public abstract DeviceGroupType getType();

    public List<Device> getDevicesByType(DeviceType deviceType) {
        return devices.stream()
                .filter(d -> d.getDeviceType().equals(deviceType))
                .toList();
    }

}
