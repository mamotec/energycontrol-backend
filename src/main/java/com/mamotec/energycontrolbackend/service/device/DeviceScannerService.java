package com.mamotec.energycontrolbackend.service.device;

import com.mamotec.energycontrolbackend.client.NodeRedClient;
import com.mamotec.energycontrolbackend.domain.device.dao.DeviceCreateRequest;
import com.mamotec.energycontrolbackend.domain.device.dao.DeviceScanDao;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.InterfaceConfig;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.dao.Interface;
import com.mamotec.energycontrolbackend.repository.DeviceRepository;
import com.mamotec.energycontrolbackend.service.interfaceconfig.InterfaceConfigService;
import com.mamotec.energycontrolbackend.service.interfaceconfig.InterfaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeviceScannerService {

    private final InterfaceConfigService interfaceConfigService;

    private final InterfaceService interfaceService;

    private final NodeRedClient nodeRedClient;

    private final DeviceRepository deviceRepository;

    private final DeviceService deviceService;

    public DeviceScanDao deviceScan() {
        List<InterfaceConfig> interfaces = interfaceConfigService.findAll();

        for (InterfaceConfig interfaceConfig : interfaces) {
            log.info("Scanning interface {}...", interfaceConfig.getType());
            return scan(interfaceConfig);
        }

        // TODO: Throw exception if no interface is available
        return null;
    }

    private DeviceScanDao scan(InterfaceConfig config) {
        Interface anInterface = interfaceService.getInterfaceByProtocolId(config.getProtocolId());
        DeviceScanDao dao = new DeviceScanDao();

        for (int slaveAddress = 1; slaveAddress <= config.getType()
                .getMaxDevices(); slaveAddress++) {
            boolean isDeviceAvailable = nodeRedClient.checkDevice(slaveAddress, config, anInterface);
            if (isDeviceAvailable) {
                if (deviceRepository.existsByUnitIdAndInterfaceConfigType(slaveAddress, config.getType())) {
                    log.info("Device with unitId {} and interface {} already exists in database", slaveAddress, config.getType());
                    DeviceCreateRequest d = new DeviceCreateRequest();
                    d.setUnitId(slaveAddress);
                    d.setInterfaceConfig(config);
                    dao.getAlreadyExistingDevices().add(d);
                } else {
                    log.info("Device with unitId {} and interface {} does not exist in database", slaveAddress, config.getType());
                    DeviceCreateRequest d = new DeviceCreateRequest();
                    d.setUnitId(slaveAddress);
                    d.setInterfaceConfig(config);
                    dao.getNewDevices().add(d);
                }
            }
        }
        return dao;
    }


}
