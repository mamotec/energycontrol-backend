package com.mamotec.energycontrolbackend.ocpp;

import eu.chargetime.ocpp.feature.profile.ClientSmartChargingEventHandler;
import eu.chargetime.ocpp.model.smartcharging.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class OcppSmartChargeEventHandler implements ClientSmartChargingEventHandler {

    @Override
    public SetChargingProfileConfirmation handleSetChargingProfileRequest(SetChargingProfileRequest request) {
        log.info("SetChargingProfileRequest: {}", request);
        return new SetChargingProfileConfirmation(ChargingProfileStatus.Accepted);
    }

    @Override
    public ClearChargingProfileConfirmation handleClearChargingProfileRequest(ClearChargingProfileRequest request) {
        log.info("ClearChargingProfileRequest: {}", request);
        return new ClearChargingProfileConfirmation(ClearChargingProfileStatus.Accepted);
    }

    @Override
    public GetCompositeScheduleConfirmation handleGetCompositeScheduleRequest(GetCompositeScheduleRequest request) {
        log.info("GetCompositeScheduleRequest: {}", request);
        return new GetCompositeScheduleConfirmation(GetCompositeScheduleStatus.Accepted);
    }
}
