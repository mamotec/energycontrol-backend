package com.mamotec.energycontrolbackend.ocpp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OcppServerConfig {

    @Bean
    public int ocppServerPort() {
        return 8887;
    }


}
