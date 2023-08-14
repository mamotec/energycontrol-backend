package com.mamotec.energycontrolbackend.controller;

import com.mamotec.energycontrolbackend.domain.interfaceconfig.dao.Interface;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.dao.InterfaceConfigDao;
import com.mamotec.energycontrolbackend.mapper.InterfaceConfigMapper;
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
@RequestMapping("/interface")
@RequiredArgsConstructor
@Slf4j
@SecurityRequirement(name = "bearerAuth")
public class InterfaceController {

    private final InterfaceService interfaceService;

    private final InterfaceConfigService interfaceConfigService;

    private final InterfaceConfigMapper interfaceConfigMapper;

    @GetMapping
    @Operation(summary = "Lade alle verfügbaren Schnittstellen")
    public ResponseEntity<List<Interface>> fetchInterfaces() {
        log.info("GET /interface is being called.");
        return ResponseEntity.ok(interfaceService.getAllInterfaces());
    }

    @GetMapping("/config")
    @Operation(summary = "Lade alle Schnittstellen erstellten Schnittstellen konfigurationen")
    public ResponseEntity<List<InterfaceConfigDao>> fetchInterfaceConfigs() {
        log.info("GET /interface/config is being called.");
        return ResponseEntity.ok(interfaceConfigMapper.map(interfaceConfigService.findAll()));
    }

    @PostMapping("/config")
    @Operation(summary = "Erstelle eine neue Schnittstellen konfiguration für die angegebene Schnittstelle")
    public ResponseEntity<InterfaceConfigDao> createInterfaceConfig(@RequestBody InterfaceConfigDao interfaceConfig) {
        log.info("POST /interface/config is being called.");
        return ResponseEntity.ok(interfaceConfigMapper.map(interfaceConfigService.save(interfaceConfigMapper.map(interfaceConfig))));
    }

}
