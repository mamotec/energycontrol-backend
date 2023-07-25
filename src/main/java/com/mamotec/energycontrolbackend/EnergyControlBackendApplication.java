package com.mamotec.energycontrolbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EnergyControlBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnergyControlBackendApplication.class, args);
	}

}
