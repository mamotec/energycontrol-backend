package com.mamotec.energycontrolbackend.domain.group;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.mamotec.energycontrolbackend.domain.BaseEntity;
import com.mamotec.energycontrolbackend.domain.device.Device;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@Table(name = "device_group")
public class DeviceGroup extends BaseEntity {

    private String name;

    @Enumerated(EnumType.STRING)
    private DeviceGroupType type;

    @OneToMany(mappedBy = "deviceGroup", fetch = FetchType.EAGER)
    private List<Device> devices;

}
