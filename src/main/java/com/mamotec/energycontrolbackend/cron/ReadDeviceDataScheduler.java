package com.mamotec.energycontrolbackend.cron;

import com.mamotec.energycontrolbackend.domain.interfaceconfig.InterfaceConfig;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.InterfaceType;
import com.mamotec.energycontrolbackend.reader.TcpDeviceDataReader;
import com.mamotec.energycontrolbackend.service.interfaceconfig.InterfaceConfigService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReadDeviceDataScheduler {

    private final InterfaceConfigService interfaceConfigService;
    private final TcpDeviceDataReader tcpDeviceDataReader;

    @Scheduled(cron = "*/1 * * * * *")
    @Transactional
    public void fetchDeviceData() {
        List<InterfaceConfig> configs = interfaceConfigService.findAll();

        for (InterfaceConfig config : configs) {
            if (config.getType()
                    .equals(InterfaceType.TCP)) {
                tcpDeviceDataReader.fetchDeviceData(config);
            }
        }
    }

}
