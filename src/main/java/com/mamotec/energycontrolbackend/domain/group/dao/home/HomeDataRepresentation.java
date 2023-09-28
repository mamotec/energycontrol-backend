package com.mamotec.energycontrolbackend.domain.group.dao.home;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mamotec.energycontrolbackend.domain.group.dao.DeviceGroupRepresentation;
import lombok.Builder;
import lombok.Data;

import java.util.function.Function;

@Data
@Builder
public class HomeDataRepresentation extends DeviceGroupRepresentation {
    // Grid
    @JsonProperty(required = true)
    private BiDirectionalEnergy grid;
    // Inverter
    @JsonProperty(required = true)
    private long peakKilowatt;
    @JsonProperty(required = true)
    private long activePower;
    // Battery
    @JsonProperty(required = true)
    private long batterySoc;
    @JsonProperty(required = true)
    private BiDirectionalEnergy batteryPower;
    // Houshold
    @JsonProperty(required = true)
    private long houseHoldPower;
    // Heatpump
    @JsonProperty(required = true)
    private boolean heatPumpActive;
    // Charging station
    @JsonProperty(required = true)
    private BiDirectionalEnergy chargingStation;

}
