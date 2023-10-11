package com.mamotec.energycontrolbackend.ocpp;

import com.mamotec.energycontrolbackend.service.device.ChargingStationService;
import eu.chargetime.ocpp.feature.profile.ServerCoreEventHandler;
import eu.chargetime.ocpp.model.Request;
import eu.chargetime.ocpp.model.core.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.ZonedDateTime;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@Slf4j
public class OcppServerCoreEventHandler implements ServerCoreEventHandler {

    private OcppSender sender;
    private OcppRequestListener<Request> listener;
    private final Set<String> identifiers;
    private final Set<String> tags;
    private final ChargingStationService service;

    @Override
    public AuthorizeConfirmation handleAuthorizeRequest(UUID uuid, AuthorizeRequest authorizeRequest) {
        log.info("AuthorizeRequest: {}", authorizeRequest);
        return null;
    }

    @Override
    public BootNotificationConfirmation handleBootNotificationRequest(UUID uuid, BootNotificationRequest bootNotificationRequest) {
        log.info("BootNotificationRequest: {}", bootNotificationRequest);
        return null;
    }

    @Override
    public DataTransferConfirmation handleDataTransferRequest(UUID uuid, DataTransferRequest dataTransferRequest) {
        log.info("DataTransferRequest: {}", dataTransferRequest);
        return null;
    }

    @Override
    public HeartbeatConfirmation handleHeartbeatRequest(UUID uuid, HeartbeatRequest heartbeatRequest) {
        log.info("HeartbeatRequest: {}", heartbeatRequest);
        service.updateActiveStatus(uuid, heartbeatRequest.validate());
        return new HeartbeatConfirmation(ZonedDateTime.now());
    }

    @Override
    public MeterValuesConfirmation handleMeterValuesRequest(UUID uuid, MeterValuesRequest meterValuesRequest) {
        log.info("MeterValuesRequest: {}", meterValuesRequest);
        return null;
    }

    @Override
    public StartTransactionConfirmation handleStartTransactionRequest(UUID uuid, StartTransactionRequest startTransactionRequest) {
        log.info("StartTransactionRequest: {}", startTransactionRequest);
        return new StartTransactionConfirmation(new IdTagInfo(AuthorizationStatus.Accepted), 1);
    }

    @Override
    public StatusNotificationConfirmation handleStatusNotificationRequest(UUID uuid, StatusNotificationRequest statusNotificationRequest) {
        log.info("StatusNotificationRequest: {}", statusNotificationRequest);
        service.updateChargePointStatus(uuid, statusNotificationRequest.getStatus());
        return new StatusNotificationConfirmation();
    }

    @Override
    public StopTransactionConfirmation handleStopTransactionRequest(UUID uuid, StopTransactionRequest stopTransactionRequest) {
        log.info("StopTransactionRequest: {}", stopTransactionRequest);
        return null;
    }
}