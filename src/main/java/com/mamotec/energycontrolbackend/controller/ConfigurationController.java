package com.mamotec.energycontrolbackend.controller;

import com.mamotec.energycontrolbackend.domain.configuration.Configuration;
import com.mamotec.energycontrolbackend.service.configuration.ConfigurationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/configuration")
@RequiredArgsConstructor
@Slf4j
@SecurityRequirement(name = "bearerAuth")
public class ConfigurationController {

    private final ConfigurationService configurationService;

    @GetMapping
    @Operation(summary = "Liefere Konfiguration")
    public ResponseEntity<Configuration> getConfiguration() {
        log.info("GET /configuration is being called.");
        return ResponseEntity.ok(configurationService.get());
    }

    @PutMapping
    @Operation(summary = "Aktualisiere Konfiguration")
    public ResponseEntity<Configuration> updateConfiguration(@RequestBody Configuration configuration) {
        log.info("PUT /configuration is being called.");
        return ResponseEntity.ok(configurationService.save(configuration));
    }



}
