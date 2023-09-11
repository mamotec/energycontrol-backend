package com.mamotec.energycontrolbackend.domain.configuration;

import com.mamotec.energycontrolbackend.domain.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@Table(name = "configuration")
public class Configuration extends BaseEntity {

    @NotNull
    private boolean directMarketing;

}