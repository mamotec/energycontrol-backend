package com.mamotec.energycontrolbackend.domain.device.chargingstation;

import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.device.DeviceType;
import eu.chargetime.ocpp.model.core.ChargePointStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

import static com.mamotec.energycontrolbackend.domain.device.DeviceType.CHARGING_STATION;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Table(name = "charging_station_device")
@DiscriminatorValue("CHARGING_STATION")
public class ChargingStationDevice extends Device {

    private String deviceIdCharger;
    // Session UUID
    private UUID uuid;
    private boolean ocppAvailable;
    @Enumerated(EnumType.STRING)
    private ChargePointStatus chargePointStatus;

    private int transactionId = 0;

    private boolean transactionActive;

    @Min(1)
    @Max(16)
    private int managedStrength = 16;

    @Override
    public DeviceType getDeviceType() {
        return CHARGING_STATION;
    }
}
