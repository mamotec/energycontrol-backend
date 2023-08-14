package com.mamotec.energycontrolbackend.domain.group;

import com.mamotec.energycontrolbackend.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@Table(name = "groups")
public class Group extends BaseEntity {

    private String name;

    @Enumerated(EnumType.STRING)
    private GroupType type;


}
