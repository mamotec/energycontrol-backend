package com.mamotec.energycontrolbackend.controller;

import com.mamotec.energycontrolbackend.domain.device.dao.DeviceCreateRequest;
import com.mamotec.energycontrolbackend.domain.device.dao.DeviceCreateResponse;
import com.mamotec.energycontrolbackend.mapper.DeviceMapper;
import com.mamotec.energycontrolbackend.service.device.DeviceService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/device")
@RequiredArgsConstructor
@Slf4j
@SecurityRequirement(name = "bearerAuth")
public class DeviceController {

    private final DeviceService deviceService;

    private final DeviceMapper deviceMapper;

    @PostMapping
    public ResponseEntity<DeviceCreateResponse> createDevice(@RequestBody DeviceCreateRequest request) {
        log.info("POST /device is being called.");
        return ResponseEntity.ok(deviceService.create(deviceMapper.map(request)));
    }

    @GetMapping("/service")
    public ResponseEntity<Boolean> isServiceAvailable() {
        log.info("GET /device/service is being called.");
        return ResponseEntity.ok(deviceService.isServiceAvailable());
    }
}
