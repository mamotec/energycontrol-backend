package com.mamotec.energycontrolbackend.domain.group.dao.home;

import com.mamotec.energycontrolbackend.domain.group.dao.DeviceGroupRepresentation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class HomeDataRepresentation extends DeviceGroupRepresentation {

    private long activePower;
    private long peakKilowatt;
    private long batterySoc;
    private long batteryPower;

}
