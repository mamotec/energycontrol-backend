package com.mamotec.energycontrolbackend.config;

import com.mamotec.energycontrolbackend.client.InfluxClient;
import com.mamotec.energycontrolbackend.client.NodeRedClient;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    private final NodeRedClient nodeRedClient;
    private final InfluxClient influxClient;

    @Override
    public void onApplicationEvent(@NonNull ApplicationReadyEvent event) {
        log.info("Check if third party services are available...");
        checkIfInfluxDbIsAvailable();
        checkIfNodeRedIsAvailable();
        log.info("All third party services are available.");
    }

    private void checkIfInfluxDbIsAvailable() {
        if (!influxClient.isInfluxDbAvailable(false)) {
            System.exit(0);
        }
    }

    private void checkIfNodeRedIsAvailable() {
        if (!nodeRedClient.isNodeRedAvailable(false)) {
            System.exit(0);
        }
    }


}
