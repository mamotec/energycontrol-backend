package com.mamotec.energycontrolbackend.service.device;

import com.mamotec.energycontrolbackend.domain.device.ChargingStationDevice;
import com.mamotec.energycontrolbackend.domain.device.dao.ChargingStationCreateRequest;
import com.mamotec.energycontrolbackend.repository.ChargingStationRepository;
import eu.chargetime.ocpp.model.core.ChargePointStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static java.lang.Long.parseLong;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChargingStationService {

    private final ChargingStationRepository repository;
    private final DeviceServiceFactory factory;

    @Transactional
    public void updateChargingStationUUID(String identifier, UUID uuid) {
        Optional<ChargingStationDevice> firstByDeviceIdCharger = repository.findFirstByDeviceIdCharger(parseLong(identifier.replace("/", "")));
        if (firstByDeviceIdCharger.isPresent()) {
            log.info("Charging Station with identifier: " + identifier + " already exists - updating UUID");

            ChargingStationDevice chargingStationDevice = firstByDeviceIdCharger.get();

            chargingStationDevice.setUuid(uuid);

            repository.save(chargingStationDevice);
        } else {
            log.info("Charging Station with identifier: " + identifier + " does not exist - creating new Charging Station");
            DeviceService service = factory.createService();
            ChargingStationCreateRequest request = new ChargingStationCreateRequest();
            request.setDeviceIdCharger(parseLong(identifier.replace("/", "")));
            request.setUuid(uuid);
            request.setChargePointStatus(ChargePointStatus.Available);
            request.setActive(true);
            request.setOcppAvailable(true);
            request.setName("Ladestation " + identifier.replace("/", ""));

            service.create(request);
        }

    }

    @Transactional
    public void updateActiveStatus(UUID uuid, boolean status) {
        ChargingStationDevice chargingStationDevice = getByUUID(uuid);

        if (chargingStationDevice == null) return;

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

    private ChargingStationDevice getByUUID(UUID uuid) throws RuntimeException {
        Optional<ChargingStationDevice> deviceToUpdate = repository.findFirstByUuid(uuid);

        if (deviceToUpdate.isEmpty()) {
            log.error("Charging Station with uuid: " + uuid + " not found");
            return null;
        }

        return deviceToUpdate.get();
    }

}
