package com.mamotec.energycontrolbackend.ocpp;

import com.mamotec.energycontrolbackend.service.device.chargingStation.ChargingStationService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OcppServerInitializer {

    private final ChargingStationService service;


    @EventListener(ApplicationReadyEvent.class)
    public void startOcppServer() {
        OcppServer.getInstance(service);
    }
}
