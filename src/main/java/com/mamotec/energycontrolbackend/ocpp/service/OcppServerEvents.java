package com.mamotec.energycontrolbackend.ocpp.service;

import com.mamotec.energycontrolbackend.service.device.chargingStation.ChargingStationService;
import eu.chargetime.ocpp.ServerEvents;
import eu.chargetime.ocpp.model.SessionInformation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class OcppServerEvents implements ServerEvents {

    private final ChargingStationService chargingStationService;

    @Override
    public void authenticateSession(SessionInformation sessionInformation, String s, byte[] bytes) {
        log.info("Authenticate Session");
    }

    @Override
    public void newSession(UUID uuid, SessionInformation sessionInformation) {
        log.info("New Charging Station connected with UUID: " + uuid);
        log.info("Charging Station Identifier: " + sessionInformation);
        chargingStationService.updateChargingStationUUID(sessionInformation.getIdentifier(), uuid);
    }

    @Override
    public void lostSession(UUID uuid) {
        chargingStationService.updateActiveStatus(uuid, false);
        log.info("Charging Station with UUID: " + uuid + " disconnected");
    }
}
