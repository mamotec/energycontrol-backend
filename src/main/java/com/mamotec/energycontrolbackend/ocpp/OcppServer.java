package com.mamotec.energycontrolbackend.ocpp;

import com.mamotec.energycontrolbackend.ocpp.service.OcppServerEvents;
import eu.chargetime.ocpp.JSONServer;
import eu.chargetime.ocpp.NotConnectedException;
import eu.chargetime.ocpp.OccurenceConstraintException;
import eu.chargetime.ocpp.UnsupportedFeatureException;
import eu.chargetime.ocpp.feature.profile.ServerCoreProfile;
import eu.chargetime.ocpp.model.Confirmation;
import eu.chargetime.ocpp.model.Request;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletionStage;

public class OcppServer implements OcppSender {

    private final JSONServer server;
    private final String ip;
    private final int port;

    public OcppServer(String ip, int port, OcppRequestListener<Request> listener, Set<String> identifiers, Set<String> tags) {
        this.ip = ip;
        this.port = port;
        OcppServerCoreEventHandler eventHandler = new OcppServerCoreEventHandler(this, listener, identifiers, tags);
        this.server = new JSONServer(new ServerCoreProfile(eventHandler));
    }


    public void activate() {
        server.open(ip, port, new OcppServerEvents());
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
