package com.mamotec.energycontrolbackend.ocpp;

import com.mamotec.energycontrolbackend.service.device.ChargingStationService;
import eu.chargetime.ocpp.feature.profile.ServerCoreEventHandler;
import eu.chargetime.ocpp.model.core.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.ZonedDateTime;
import java.util.UUID;

import static eu.chargetime.ocpp.model.core.AuthorizationStatus.Accepted;
import static eu.chargetime.ocpp.model.core.AuthorizationStatus.Invalid;

@AllArgsConstructor
@Slf4j
public class OcppServerCoreEventHandler implements ServerCoreEventHandler {

    private final ChargingStationService service;

    @Override
    public AuthorizeConfirmation handleAuthorizeRequest(UUID uuid, AuthorizeRequest authorizeRequest) {
        log.info("AuthorizeRequest: {}", authorizeRequest);
        log.info("idTag: {}", authorizeRequest.getIdTag());
        IdTagInfo idTagInfo = new IdTagInfo(Accepted);
        return new AuthorizeConfirmation(idTagInfo);
    }

    @Override
    public BootNotificationConfirmation handleBootNotificationRequest(UUID uuid, BootNotificationRequest bootNotificationRequest) {
        log.info("BootNotificationRequest: {}", bootNotificationRequest);
        service.updateActiveStatus(uuid, bootNotificationRequest.validate());
        if (bootNotificationRequest.validate())
            return new BootNotificationConfirmation(ZonedDateTime.now(), 5, RegistrationStatus.Accepted);
        else
            return new BootNotificationConfirmation(ZonedDateTime.now(), 5, RegistrationStatus.Rejected);
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
        for (MeterValue meterValue : meterValuesRequest.getMeterValue()) {
            log.info("MeterValue: {}", meterValue);
            for (SampledValue sampledValue : meterValue.getSampledValue()) {
                log.info("SampledValue: {}", sampledValue);
            }

            }
        //service.saveMeterValue(uuid, meterValuesRequest.getMeterValue());
        return new MeterValuesConfirmation();
    }

    @Override
    public StatusNotificationConfirmation handleStatusNotificationRequest(UUID uuid, StatusNotificationRequest statusNotificationRequest) {
        log.info("StatusNotificationRequest: {}", statusNotificationRequest);
        service.updateChargePointStatus(uuid, statusNotificationRequest.getStatus());
        return new StatusNotificationConfirmation();
    }

    @Override
    public DataTransferConfirmation handleDataTransferRequest(UUID uuid, DataTransferRequest dataTransferRequest) {
        log.info("DataTransferRequest: {}", dataTransferRequest);
        return new DataTransferConfirmation(DataTransferStatus.Accepted);
    }

    @Override
    public StartTransactionConfirmation handleStartTransactionRequest(UUID uuid, StartTransactionRequest startTransactionRequest) {
        log.info("StartTransactionRequest: {}", startTransactionRequest);

        int transactionId = service.startTransaction(uuid);
        return new StartTransactionConfirmation(new IdTagInfo(transactionId == 0 ? Invalid : Accepted), transactionId);

    }

    @Override
    public StopTransactionConfirmation handleStopTransactionRequest(UUID uuid, StopTransactionRequest stopTransactionRequest) {
        log.info("StopTransactionRequest: {}", stopTransactionRequest);
        boolean canStop = service.stopTransaction(uuid, stopTransactionRequest.getTransactionId());

        StopTransactionConfirmation stopTransactionConfirmation = new StopTransactionConfirmation();
        stopTransactionConfirmation.setIdTagInfo(new IdTagInfo(canStop ? Accepted : Invalid));
        return stopTransactionConfirmation;
    }


}
