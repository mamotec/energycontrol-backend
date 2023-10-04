package com.mamotec.energycontrolbackend.ocpp;

import com.mamotec.energycontrolbackend.service.device.ChargingStationService;
import eu.chargetime.ocpp.feature.profile.ServerCoreEventHandler;
import eu.chargetime.ocpp.model.Request;
import eu.chargetime.ocpp.model.core.*;
import lombok.AllArgsConstructor;

import java.time.ZonedDateTime;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
public class OcppServerCoreEventHandler implements ServerCoreEventHandler {

    private OcppSender sender;
    private OcppRequestListener<Request> listener;
    private final Set<String> identifiers;
    private final Set<String> tags;
    private final ChargingStationService service;

    @Override
    public AuthorizeConfirmation handleAuthorizeRequest(UUID uuid, AuthorizeRequest authorizeRequest) {
        return null;
    }

    @Override
    public BootNotificationConfirmation handleBootNotificationRequest(UUID uuid, BootNotificationRequest bootNotificationRequest) {
        return null;
    }

    @Override
    public DataTransferConfirmation handleDataTransferRequest(UUID uuid, DataTransferRequest dataTransferRequest) {
        return null;
    }

    @Override
    public HeartbeatConfirmation handleHeartbeatRequest(UUID uuid, HeartbeatRequest heartbeatRequest) {
        service.updateStatus(uuid, heartbeatRequest.validate());
        return new HeartbeatConfirmation(ZonedDateTime.now());
    }

    @Override
    public MeterValuesConfirmation handleMeterValuesRequest(UUID uuid, MeterValuesRequest meterValuesRequest) {
        return null;
    }

    @Override
    public StartTransactionConfirmation handleStartTransactionRequest(UUID uuid, StartTransactionRequest startTransactionRequest) {
        return new StartTransactionConfirmation(new IdTagInfo(AuthorizationStatus.Accepted), 1);
    }

    @Override
    public StatusNotificationConfirmation handleStatusNotificationRequest(UUID uuid, StatusNotificationRequest statusNotificationRequest) {
        return new StatusNotificationConfirmation();
    }

    @Override
    public StopTransactionConfirmation handleStopTransactionRequest(UUID uuid, StopTransactionRequest stopTransactionRequest) {
        return null;
    }
}
