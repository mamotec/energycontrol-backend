package com.mamotec.energycontrolbackend.ocpp;

import com.mamotec.energycontrolbackend.service.device.ChargingStationService;
import eu.chargetime.ocpp.FeatureRepository;
import eu.chargetime.ocpp.feature.profile.ClientSmartChargingProfile;
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
        FeatureRepository featureRepository = new FeatureRepository();
        OcppServer.getInstance(service);
    }
}
