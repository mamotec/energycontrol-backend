package com.mamotec.energycontrolbackend.domain.device;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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

    private long deviceIdCharger;
    // UUID from charging station
    private UUID uuid;

    @Override
    public DeviceType getDeviceType() {
        return CHARGING_STATION;
    }
}
