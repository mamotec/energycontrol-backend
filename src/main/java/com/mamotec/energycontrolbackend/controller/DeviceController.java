package com.mamotec.energycontrolbackend.controller;

import com.mamotec.energycontrolbackend.client.NodeRedClient;
import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.device.dao.DeviceCreateRequest;
import com.mamotec.energycontrolbackend.domain.device.dao.DeviceCreateResponse;
import com.mamotec.energycontrolbackend.mapper.DeviceMapper;
import com.mamotec.energycontrolbackend.service.device.DeviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/device")
@RequiredArgsConstructor
@Slf4j
@SecurityRequirement(name = "bearerAuth")
public class DeviceController {

    private final DeviceService deviceService;

    private final DeviceMapper deviceMapper;

    private final NodeRedClient nodeRedClient;

    @PostMapping
    @Operation(summary = "Erstelle ein neues Gerät")
    public ResponseEntity<DeviceCreateResponse> createDevice(@RequestBody DeviceCreateRequest request) {
        log.info("POST /device is being called.");
        return ResponseEntity.ok(deviceService.create(deviceMapper.map(request)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Lösche ein Gerät (Soft Delete))")
    public ResponseEntity<Void> deleteDevice(@PathVariable Long id) {
        log.info("DELETE /device/{} is being called.", id);
        deviceService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @Operation(summary = "Lade alle verfügbaren Geräte")
    public ResponseEntity<List<Device>> fetchDevices() {
        log.info("GET /device is being called.");
        return ResponseEntity.ok(deviceService.getAllDevices());
    }

    @GetMapping("/group/{id}")
    @Operation(summary = "Lade alle validen Geräte für eine Gruppe")
    public ResponseEntity<List<Device>> fetchDevicesForGroup(@PathVariable Long id) {
        log.info("GET /device/group/{} is being called.", id);
        return ResponseEntity.ok(deviceService.getValidDevicesForGroup(id));
    }

    @GetMapping("/service")
    @Operation(summary = "Prüfe ob NodeRED verfügbar ist")
    public ResponseEntity<Boolean> isServiceAvailable() {
        log.info("GET /device/service is being called.");
        return ResponseEntity.ok(nodeRedClient.isNodeRedAvailable(false));
    }
}
