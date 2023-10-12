package com.mamotec.energycontrolbackend.ocpp;

import com.mamotec.energycontrolbackend.ocpp.service.OcppServerEvents;
import com.mamotec.energycontrolbackend.service.device.ChargingStationService;
import eu.chargetime.ocpp.JSONServer;
import eu.chargetime.ocpp.NotConnectedException;
import eu.chargetime.ocpp.OccurenceConstraintException;
import eu.chargetime.ocpp.UnsupportedFeatureException;
import eu.chargetime.ocpp.feature.profile.ClientSmartChargingProfile;
import eu.chargetime.ocpp.feature.profile.ServerCoreProfile;
import eu.chargetime.ocpp.model.Confirmation;
import eu.chargetime.ocpp.model.Request;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;
import java.util.concurrent.CompletionStage;

@Slf4j
public class OcppServer implements OcppSender {

    private static JSONServer server;

    public static JSONServer getInstance(ChargingStationService service) {
        if (server == null) {
            server = new JSONServer(new ServerCoreProfile(new OcppServerCoreEventHandler(service)));
            server.addFeatureProfile(new ClientSmartChargingProfile(new OcppSmartChargeEventHandler()));
            server.open("0.0.0.0", 8887, new OcppServerEvents(service));
            log.info("OcppServer started on port: " + 8887);
        }
        return server;
    }

    @Override
    public CompletionStage<Confirmation> send(UUID sessionIndex, Request request) {
        try {
            return server.send(sessionIndex, request);
        } catch (OccurenceConstraintException | UnsupportedFeatureException | NotConnectedException e) {
            throw new RuntimeException(e);
        }
    }
}
