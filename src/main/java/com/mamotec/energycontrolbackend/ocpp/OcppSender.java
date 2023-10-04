package com.mamotec.energycontrolbackend.ocpp;

import eu.chargetime.ocpp.model.Confirmation;
import eu.chargetime.ocpp.model.Request;

import java.util.UUID;
import java.util.concurrent.CompletionStage;

public interface OcppSender {

    <T extends Confirmation> CompletionStage<T> send(UUID sessionIndex, Request request);
}
