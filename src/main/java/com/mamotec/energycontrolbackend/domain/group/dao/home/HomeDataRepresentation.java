package com.mamotec.energycontrolbackend.domain.group.dao.home;

import com.mamotec.energycontrolbackend.domain.group.dao.DeviceGroupRepresentation;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HomeDataRepresentation extends DeviceGroupRepresentation {

    // Group
    private long peakKilowatt;
    // Inverter
    private long activePower;
    // Battery
    private long batterySoc;
    private long batteryPower;
    // Houshold
    private long houseHoldPower;
    // Heatpump
    private boolean heatPumpActive;
    // Charging station
    private long chargingStationPower;

}
