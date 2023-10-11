package com.mamotec.energycontrolbackend.ocpp;

import com.mamotec.energycontrolbackend.ocpp.config.OcppServerConfig;
import com.mamotec.energycontrolbackend.service.device.ChargingStationService;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@AllArgsConstructor
public class OcppServerInitializer {

    private final OcppServerConfig config;
    private final ChargingStationService service;


    @EventListener(ApplicationReadyEvent.class)
    public void startOcppServer() {
        OcppServer server = new OcppServer("0.0.0.0", config.ocppServerPort(), null, Collections.emptySet(), Collections.emptySet(), service);
        server.activate(service);
    }
}
