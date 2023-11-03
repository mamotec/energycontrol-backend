package com.mamotec.energycontrolbackend.service.device;

import com.mamotec.energycontrolbackend.domain.device.chargingstation.ChargingStationDevice;
import com.mamotec.energycontrolbackend.domain.device.dao.ChargingStationCreateRequest;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.yaml.Unit;
import com.mamotec.energycontrolbackend.repository.ChargingStationRepository;
import eu.chargetime.ocpp.model.core.ChargePointStatus;
import eu.chargetime.ocpp.model.core.MeterValue;
import eu.chargetime.ocpp.model.core.SampledValue;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChargingStationService {

    private final ChargingStationRepository repository;
    private final DeviceServiceFactory factory;
    private final DeviceDataWriteService writeService;

    @Transactional
    public void updateChargingStationUUID(String identifier, UUID uuid) {
        String result = "";
        int lastSlashIndex = identifier.lastIndexOf('/');
        if (lastSlashIndex >= 0 && lastSlashIndex < identifier.length() - 1) {  // Überprüfen, ob es ein letztes / gibt und es nicht das letzte Zeichen ist
            result = identifier.substring(lastSlashIndex + 1);
        } else {
            log.error("Kein Slash gefunden oder Slash ist das letzte Zeichen!");
        }
        Optional<ChargingStationDevice> firstByDeviceIdCharger = repository.findFirstByDeviceIdCharger(result);
        if (firstByDeviceIdCharger.isPresent()) {
            log.info("Charging Station with identifier: " + identifier + " already exists - updating UUID");

            ChargingStationDevice chargingStationDevice = firstByDeviceIdCharger.get();

            chargingStationDevice.setUuid(uuid);

            repository.save(chargingStationDevice);
        } else {
            log.info("Charging Station with identifier: " + identifier + " does not exist - creating new Charging Station");
            DeviceService service = factory.createService();
            ChargingStationCreateRequest request = new ChargingStationCreateRequest();
            request.setDeviceIdCharger(result);
            request.setUuid(uuid);
            request.setChargePointStatus(ChargePointStatus.Available);
            request.setActive(true);
            request.setOcppAvailable(true);
            request.setName("Ladestation " + result);

            service.create(request);
        }

    }

    @Transactional
    public void updateActiveStatus(UUID uuid, boolean status) {
        ChargingStationDevice chargingStationDevice = getByUUID(uuid);

        if (chargingStationDevice == null){
            log.info("Charging Station with uuid: " + uuid + " not found");
            return;
        }

        log.info("Charging Station with uuid: " + uuid + " found");

        chargingStationDevice.setActive(status);

        repository.save(chargingStationDevice);
    }

    @Transactional
    public void updateChargePointStatus(UUID uuid, ChargePointStatus status) {
        ChargingStationDevice chargingStationDevice = getByUUID(uuid);

        if (chargingStationDevice == null) return;

        chargingStationDevice.setChargePointStatus(status);

        repository.save(chargingStationDevice);
    }

    @Transactional
    public int startTransaction(UUID uuid) {
        ChargingStationDevice cs = getByUUID(uuid);

        // Check if a transaction is ongoing.
        if (Objects.requireNonNull(cs)
                .isTransactionActive()) {
            return cs.getTransactionId();
        }

        cs.setTransactionId(cs.getTransactionId() + 1);
        cs.setTransactionActive(true);
        repository.save(cs);
        return cs.getTransactionId();
    }

    @Transactional
    public boolean stopTransaction(UUID uuid, int transactionId) {
        ChargingStationDevice cs = getByUUID(uuid);

        if (Objects.isNull(cs)) {
            return true;
        }

        if (cs.isTransactionActive() && cs.getTransactionId() == transactionId) {
            cs.setTransactionActive(false);
            repository.save(cs);
            return true;
        } else {
            log.error("Charging Station with uuid: " + uuid + " has no active transaction");
            return false;
        }
    }

    public void saveMeterValue(UUID uuid, MeterValue[] meterValue) {
        ChargingStationDevice cs = getByUUID(uuid);

        if (Objects.isNull(cs)) {
            return;
        }

        for (MeterValue value : meterValue) {
            String collect = Arrays.stream(value.getSampledValue())
                    .filter(sv -> sv.getPhase()
                            .equals("L1"))
                    .filter(sv -> sv.getPhase()
                            .equals("L2"))
                    .filter(sv -> sv.getPhase()
                            .equals("L3"))
                    .filter(sv -> sv.getMeasurand()
                            .equals("Current.Import"))
                    .filter(sv -> sv.getUnit()
                            .equals(Unit.W.toString()))
                    .map(SampledValue::getValue)
                    .collect(Collectors.joining(","));

            Optional<String> result = Optional.of(collect);

            result.ifPresent(s -> writeService.writeDeviceData(cs, "[" + s + "]", "currentImport"));
        }
    }

    private ChargingStationDevice getByUUID(UUID uuid) throws RuntimeException {
        Optional<ChargingStationDevice> deviceToUpdate = repository.findFirstByUuid(uuid);

        if (deviceToUpdate.isEmpty()) {
            log.error("Charging Station with uuid: " + uuid + " not found");
            return null;
        }

        return deviceToUpdate.get();
    }
}
