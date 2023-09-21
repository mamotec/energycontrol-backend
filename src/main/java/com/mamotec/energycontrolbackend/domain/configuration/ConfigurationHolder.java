package com.mamotec.energycontrolbackend.domain.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigurationHolder {

    @Value("${application-mode}")
    private String applicationMode;

    public ApplicationMode getApplicationMode() {
        return ApplicationMode.valueOf(applicationMode);
    }
}
