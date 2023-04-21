package com.mamotec.energycontrolbackend.controller;

import com.mamotec.energycontrolbackend.domain.device.dao.InterfaceConfigRequest;
import com.mamotec.energycontrolbackend.domain.device.dao.InterfaceConfigResponse;
import com.mamotec.energycontrolbackend.mapper.InterfaceConfigMapper;
import com.mamotec.energycontrolbackend.service.device.InterfaceConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/device")
@RequiredArgsConstructor
@Slf4j
public class DeviceController {

    private final InterfaceConfigService interfaceConfigService;

    private final InterfaceConfigMapper interfaceConfigMapper;

    @PostMapping("/interface")
    public ResponseEntity<InterfaceConfigResponse> createInterface(@RequestBody InterfaceConfigRequest request) {
        log.info("POST /device/interface is being called.");
        return ResponseEntity.ok(interfaceConfigService.create(interfaceConfigMapper.map(request)));
    }
}
