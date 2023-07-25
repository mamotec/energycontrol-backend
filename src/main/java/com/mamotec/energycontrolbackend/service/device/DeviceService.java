package com.mamotec.energycontrolbackend.service.device;

import com.mamotec.energycontrolbackend.client.NodeRedClient;
import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.device.dao.DeviceCreateRequest;
import com.mamotec.energycontrolbackend.domain.device.dao.DeviceCreateResponse;
import com.mamotec.energycontrolbackend.mapper.DeviceMapper;
import com.mamotec.energycontrolbackend.repository.DeviceRepository;
import com.mamotec.energycontrolbackend.service.CrudOperations;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeviceService implements CrudOperations<Device> {

    private final DeviceRepository repository;

    private final DeviceMapper mapper;

    private final NodeRedClient nodeRedClient;


    public DeviceCreateResponse create(Device device) {
        return mapper.map(save(device));
    }

    @Override
    public Optional<JpaRepository<Device, Integer>> getRepository() {
        return Optional.of(repository);
    }

    public Boolean isServiceAvailable() {
        return nodeRedClient.isServiceAvailable(false);
    }
}
