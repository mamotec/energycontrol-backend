package com.mamotec.energycontrolbackend.cron;

import com.mamotec.energycontrolbackend.domain.interfaceconfig.InterfaceConfig;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.InterfaceType;
import com.mamotec.energycontrolbackend.reader.SerialDeviceDataReader;
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
    private final SerialDeviceDataReader serialDeviceDataReader;
    private final TcpDeviceDataReader tcpDeviceDataReader;

    @Scheduled(cron = "*/5 * * * * *")
    @Transactional
    public void fetchDeviceData() {
        List<InterfaceConfig> configs = interfaceConfigService.findAll();
        log.info("READ - Found {} interfaces in repository.", configs.size());

        for (InterfaceConfig config : configs) {
            if (config.getType().equals(InterfaceType.RS485)) {
                serialDeviceDataReader.fetchDeviceData(config);
            } else if (config.getType().equals(InterfaceType.TCP)) {
                tcpDeviceDataReader.fetchDeviceData(config);
            }
        }
    }

}
