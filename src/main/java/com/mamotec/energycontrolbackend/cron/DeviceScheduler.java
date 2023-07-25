package com.mamotec.energycontrolbackend.cron;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DeviceScheduler {

    private static final Logger logger = LoggerFactory.getLogger(DeviceScheduler.class);

    @Value("${node-red.url}")
    private String nodeRedUrl;

    @Scheduled(cron = "* * * * *")
    public void fetchDeviceData() {
        logger.info("Fetching device data... using node-red url: " + nodeRedUrl);


    }
}
