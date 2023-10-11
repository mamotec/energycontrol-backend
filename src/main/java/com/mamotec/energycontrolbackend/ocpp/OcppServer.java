package com.mamotec.energycontrolbackend.ocpp;

import com.mamotec.energycontrolbackend.ocpp.service.OcppServerEvents;
import com.mamotec.energycontrolbackend.service.device.ChargingStationService;
import eu.chargetime.ocpp.JSONServer;
import eu.chargetime.ocpp.NotConnectedException;
import eu.chargetime.ocpp.OccurenceConstraintException;
import eu.chargetime.ocpp.UnsupportedFeatureException;
import eu.chargetime.ocpp.feature.profile.ServerCoreProfile;
import eu.chargetime.ocpp.model.Confirmation;
import eu.chargetime.ocpp.model.Request;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletionStage;

@Slf4j
public class OcppServer implements OcppSender {

    private final JSONServer server;
    private final String ip;
    private final int port;

    public OcppServer(String ip, int port, OcppRequestListener<Request> listener, Set<String> identifiers, Set<String> tags, ChargingStationService service) {
        this.ip = ip;
        this.port = port;
        OcppServerCoreEventHandler eventHandler = new OcppServerCoreEventHandler(this, listener, identifiers, tags, service);
        this.server = new JSONServer(new ServerCoreProfile(eventHandler));
    }


    public void activate(ChargingStationService service) {
        server.open(ip, port, new OcppServerEvents(service));
        log.info("OcppServer started on port: " + port);
    }


    @Override
    public CompletionStage<Confirmation> send(UUID sessionIndex, Request request) {
        try {
            return this.server.send(sessionIndex, request);
        } catch (OccurenceConstraintException | UnsupportedFeatureException | NotConnectedException e) {
            throw new RuntimeException(e);
        }
    }
}