package com.mamotec.energycontrolbackend.service.device;

import com.mamotec.energycontrolbackend.client.NodeRedClient;
import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.device.dao.DeviceCreateResponse;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.InterfaceConfig;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.dao.Interface;
import com.mamotec.energycontrolbackend.mapper.DeviceMapper;
import com.mamotec.energycontrolbackend.repository.DeviceRepository;
import com.mamotec.energycontrolbackend.service.CrudOperations;
import com.mamotec.energycontrolbackend.service.interfaceconfig.InterfaceConfigService;
import com.mamotec.energycontrolbackend.service.interfaceconfig.InterfaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class DeviceService implements CrudOperations<Device> {

    private final DeviceRepository repository;

    private final InterfaceConfigService interfaceConfigService;

    private final InterfaceService interfaceService;

    private final DeviceMapper mapper;

    private final NodeRedClient nodeRedClient;

    public DeviceCreateResponse create(Device device) {
        return mapper.map(save(device));
    }

    public Boolean isServiceAvailable() {
        return nodeRedClient.isNodeRedAvailable(false);
    }

    public List<Device> getDevicesForInterfaceConfig(long interfaceConfigId) {
        return repository.findByInterfaceConfigId(interfaceConfigId);
    }

    public void deviceScan() {
        List<InterfaceConfig> interfaces = interfaceConfigService.findAll();

        for (InterfaceConfig interfaceConfig : interfaces) {
            log.info("Scanning interface {}...", interfaceConfig.getType());
            scan(interfaceConfig);
            log.info("Scanning interface {}... done", interfaceConfig.getType());
        }
    }

    private void scan(InterfaceConfig config) {
        Interface anInterface = interfaceService.getInterfaceByProtocolId(config.getProtocolID());

        for (int slaveAddress = 1; slaveAddress <= config.getType()
                .getMaxDevices(); slaveAddress++) {
            nodeRedClient.checkDevice(slaveAddress, config, anInterface);
        }
    }

    @Override
    public Optional<JpaRepository<Device, Integer>> getRepository() {
        return Optional.of(repository);
    }

}
