package com.mamotec.energycontrolbackend.service.device;

import com.mamotec.energycontrolbackend.client.NodeRedClient;
import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.device.dao.DeviceCreateResponse;
import com.mamotec.energycontrolbackend.domain.modbus.ModbusType;
import com.mamotec.energycontrolbackend.mapper.DeviceMapper;
import com.mamotec.energycontrolbackend.repository.DeviceRepository;
import com.mamotec.energycontrolbackend.service.CrudOperations;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.mamotec.energycontrolbackend.domain.modbus.ModbusType.RTU;
import static com.mamotec.energycontrolbackend.domain.modbus.ModbusType.TCP;

@Service
@RequiredArgsConstructor
public class DeviceService implements CrudOperations<Device> {

    private final DeviceRepository repository;

    private final DeviceMapper mapper;

    private final NodeRedClient nodeRedClient;

    @Value("${modbus.type}")
    private String modbusType;

    public DeviceCreateResponse create(Device device) {
        return mapper.map(save(device));
    }

    public Boolean isServiceAvailable() {
        return nodeRedClient.isServiceAvailable(false);
    }

    public void deviceScan() {
        ModbusType type = ModbusType.valueOf(modbusType);

        if (type.equals(TCP)) {
            scan(TCP.getMaxNumberOfSlaves());
        } else if (type.equals(RTU)) {
            scan(RTU.getMaxNumberOfSlaves());
        }
    }

    private void scan(long maxNumberOfSlaves) {
        for (int slaveAddress = 1; slaveAddress <= maxNumberOfSlaves; slaveAddress++) {
            nodeRedClient.checkDevice(slaveAddress);
        }
    }

    @Override
    public Optional<JpaRepository<Device, Integer>> getRepository() {
        return Optional.of(repository);
    }

}
