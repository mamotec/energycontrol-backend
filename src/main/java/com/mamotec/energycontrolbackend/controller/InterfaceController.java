package com.mamotec.energycontrolbackend.controller;

import com.mamotec.energycontrolbackend.domain.device.DeviceType;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.InterfaceConfig;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.yaml.DeviceYaml;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.yaml.InterfaceYaml;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.yaml.ManufacturerYaml;
import com.mamotec.energycontrolbackend.service.interfaceconfig.InterfaceConfigService;
import com.mamotec.energycontrolbackend.service.interfaceconfig.InterfaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interface")
@RequiredArgsConstructor
@Slf4j
@SecurityRequirement(name = "bearerAuth")
public class InterfaceController {

    private final InterfaceService interfaceService;

    private final InterfaceConfigService interfaceConfigService;

    // region Schnittstellen (YAML)

    @GetMapping
    @Operation(summary = "Lade alle verfügbaren Schnittstellen")
    public ResponseEntity<List<InterfaceYaml>> fetchInterfaces() {
        log.info("GET /interface is being called.");
        return ResponseEntity.ok(interfaceService.getAllInterfaces());
    }

    @GetMapping("/manufacturer")
    @Operation(summary = "Lade alle verfügbaren Hersteller")
    public ResponseEntity<List<ManufacturerYaml>> fetchManufactures() {
        log.info("GET /interface/manufacturer is being called.");
        return ResponseEntity.ok(interfaceService.getAllManufactures());
    }

    @GetMapping("/{manufacturerId}/devices/{deviceType}")
    @Operation(summary = "Lade alle verfügbaren Geräte für den angegebenen Hersteller")
    public ResponseEntity<List<DeviceYaml>> fetchDevicesForManufacturer(@PathVariable Long manufacturerId, @PathVariable DeviceType deviceType) {
        log.info("GET /interface/{}/devices?{} is being called.", manufacturerId, deviceType);
        return ResponseEntity.ok(interfaceService.getAllDevicesByManufacturerAndDeviceType(manufacturerId, deviceType));
    }

    // endregion

    // region Schnittstellen konfigurationen

    @DeleteMapping("/config/{id}")
    @Operation(summary = "Lösche die Schnittstellen konfiguration mit der angegebenen ID")
    public ResponseEntity<Void> deleteInterfaceConfig(@PathVariable Integer id) {
        log.info("DELETE /interface/config/{} is being called.", id);
        interfaceConfigService.delete(id);
        return ResponseEntity.ok()
                .build();
    }

    @GetMapping("/config")
    @Operation(summary = "Lade alle Schnittstellen erstellten Schnittstellen konfigurationen")
    public ResponseEntity<List<InterfaceConfig>> fetchInterfaceConfigs() {
        log.info("GET /interface/config is being called.");
        return ResponseEntity.ok(interfaceConfigService.findAll());
    }

    @PostMapping("/config")
    @Operation(summary = "Erstelle eine neue Schnittstellen konfiguration für die angegebene Schnittstelle")
    public ResponseEntity<InterfaceConfig> createInterfaceConfig(@RequestBody InterfaceConfig interfaceConfig) {
        log.info("POST /interface/config is being called.");
        return ResponseEntity.ok(interfaceConfigService.save(interfaceConfig));
    }

    // endregion

}
