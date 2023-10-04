package com.mamotec.energycontrolbackend.service.device;

import com.mamotec.energycontrolbackend.domain.device.ChargingStationDevice;
import com.mamotec.energycontrolbackend.repository.ChargingStationRepository;
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

    public void updateChargingStationUUID(String identifier, UUID uuid) {
        Optional<ChargingStationDevice> deviceToUpdate = repository.findFirstByDeviceIdCharger(parseLong(identifier.replace("/", "")));

        if (deviceToUpdate.isEmpty()) {
            log.info("Charging Station with identifier: %s not found".formatted(identifier));
            return;
        }

        ChargingStationDevice chargingStationDevice = deviceToUpdate.get();

        chargingStationDevice.setUuid(uuid);

        repository.save(chargingStationDevice);
    }

    public void updateStatus(UUID uuid, boolean status) {
        ChargingStationDevice chargingStationDevice = getByUUID(uuid);

        if (chargingStationDevice == null) return;

        chargingStationDevice.setActive(status);

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
