package com.mamotec.energycontrolbackend.domain.group;

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
    private GroupType type;

    @OneToMany(mappedBy = "deviceGroup")
    private List<Device> devices;

}
