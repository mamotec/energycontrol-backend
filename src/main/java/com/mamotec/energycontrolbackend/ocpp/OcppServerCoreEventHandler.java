package com.mamotec.energycontrolbackend.ocpp;

import com.mamotec.energycontrolbackend.service.device.ChargingStationService;
import eu.chargetime.ocpp.feature.profile.ServerCoreEventHandler;
import eu.chargetime.ocpp.model.core.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.ZonedDateTime;
import java.util.UUID;

@AllArgsConstructor
@Slf4j
public class OcppServerCoreEventHandler implements ServerCoreEventHandler {

    private final ChargingStationService service;

    @Override
    public AuthorizeConfirmation handleAuthorizeRequest(UUID uuid, AuthorizeRequest authorizeRequest) {
        log.info("AuthorizeRequest: {}", authorizeRequest);
        log.info("idTag: {}", authorizeRequest.getIdTag());
        IdTagInfo idTagInfo = new IdTagInfo(AuthorizationStatus.Accepted);
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
        for (MeterValue meterValue : meterValuesRequest.getMeterValue()) {
            for (SampledValue sampledValue : meterValue.getSampledValue()) {
                log.info("SampledValue: {}", sampledValue);
            }

            }
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

        if (service.isChargingStationUnmanaged(uuid)) {
            return new StartTransactionConfirmation(new IdTagInfo(AuthorizationStatus.Accepted), 1);
        }

        return new StartTransactionConfirmation(new IdTagInfo(AuthorizationStatus.Invalid), 1);

    }

    @Override
    public StopTransactionConfirmation handleStopTransactionRequest(UUID uuid, StopTransactionRequest stopTransactionRequest) {
        log.info("StopTransactionRequest: {}", stopTransactionRequest);
        return new StopTransactionConfirmation();
    }


}
