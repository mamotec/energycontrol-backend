package com.mamotec.energycontrolbackend.domain.group.dao.home;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BiDirectionalEnergy {

    @JsonProperty(required = true)
    private long value;

    @JsonProperty(required = true)
    private boolean consumption;
}
