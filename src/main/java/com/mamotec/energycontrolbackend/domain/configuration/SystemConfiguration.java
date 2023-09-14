package com.mamotec.energycontrolbackend.domain.configuration;

import com.mamotec.energycontrolbackend.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@Table(name = "system_configuration")
public class SystemConfiguration extends BaseEntity {

    private boolean directMarketing;

    private boolean feedInManagement;

}
