package com.mamotec.energycontrolbackend.controller;

import com.mamotec.energycontrolbackend.domain.device.dao.InterfaceConfigRequest;
import com.mamotec.energycontrolbackend.domain.device.dao.InterfaceConfigResponse;
import com.mamotec.energycontrolbackend.mapper.InterfaceConfigMapper;
import com.mamotec.energycontrolbackend.service.device.InterfaceConfigService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/device")
@RequiredArgsConstructor
@Slf4j
@SecurityRequirement(name = "basicAuth")
public class DeviceController {

    private final InterfaceConfigService interfaceConfigService;

    private final InterfaceConfigMapper interfaceConfigMapper;

    @PostMapping("/interface")
    public ResponseEntity<InterfaceConfigResponse> createInterface(@RequestBody InterfaceConfigRequest request) {
        log.info("POST /device/interface is being called.");
        return ResponseEntity.ok(interfaceConfigService.create(interfaceConfigMapper.map(request)));
    }
}
