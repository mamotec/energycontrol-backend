package com.mamotec.energycontrolbackend.domain.device.dao;

import eu.chargetime.ocpp.model.core.ChargePointStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChargingStationCreateRequest extends DeviceCreateRequest {

    private String deviceIdCharger;
    private boolean ocppAvailable;
    private UUID uuid;
    private ChargePointStatus chargePointStatus;
    private long transactionId;
    private boolean transactionActive;

}
