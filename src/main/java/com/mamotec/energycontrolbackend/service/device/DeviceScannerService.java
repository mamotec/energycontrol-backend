package com.mamotec.energycontrolbackend.service.device;

import com.mamotec.energycontrolbackend.client.NodeRedClient;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.InterfaceConfig;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.dao.Interface;
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

    public void deviceScan() {
        List<InterfaceConfig> interfaces = interfaceConfigService.findAll();

        for (InterfaceConfig interfaceConfig : interfaces) {
            log.info("Scanning interface {}...", interfaceConfig.getType());
            scan(interfaceConfig);
            log.info("Scanning interface {}... done", interfaceConfig.getType());
        }
    }

    private void scan(InterfaceConfig config) {
        Interface anInterface = interfaceService.getInterfaceByProtocolId(config.getProtocolId());

        for (int slaveAddress = 1; slaveAddress <= config.getType()
                .getMaxDevices(); slaveAddress++) {
            nodeRedClient.checkDevice(slaveAddress, config, anInterface);
        }
    }
}
