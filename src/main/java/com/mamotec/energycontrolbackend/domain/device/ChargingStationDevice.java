package com.mamotec.energycontrolbackend.domain.device;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Table(name = "charging_station_device")
public class ChargingStationDevice extends TcpDevice {

    private long deviceIdCharger;

}
