package com.mamotec.energycontrolbackend.controller;

import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.device.DeviceType;
import com.mamotec.energycontrolbackend.domain.device.dao.DeviceCreateRequest;
import com.mamotec.energycontrolbackend.domain.device.dao.DeviceTypeResponse;
import com.mamotec.energycontrolbackend.domain.device.dao.DeviceUpdateRequest;
import com.mamotec.energycontrolbackend.domain.device.dao.EnergyDistributionResponse;
import com.mamotec.energycontrolbackend.service.device.DeviceService;
import com.mamotec.energycontrolbackend.service.device.DeviceServiceFactory;
import com.mamotec.energycontrolbackend.service.device.plant.PlantDeviceService;
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

    private final PlantDeviceService plantDeviceService;

    private final DeviceServiceFactory factory;

    @PostMapping
    @Operation(summary = "Erstelle ein neues Gerät")
    public ResponseEntity<Void> createDevice(@RequestBody DeviceCreateRequest request) {
        log.info("POST /device is being called.");
        DeviceService service = factory.createService();
        service.create(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Aktualisiere ein Gerät")
    public ResponseEntity<Void> updateDevice(@PathVariable Long id, @RequestBody DeviceUpdateRequest request) {
        log.info("PUT /device/{} is being called.", id);
        DeviceService service = factory.createService();
        service.update(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Lösche ein Gerät (Soft Delete))")
    public ResponseEntity<Void> deleteDevice(@PathVariable Long id) {
        log.info("DELETE /device/{} is being called.", id);
        DeviceService service = factory.createService();
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @Operation(summary = "Lade alle verfügbaren Geräte")
    public ResponseEntity<List<Device>> fetchDevices() {
        log.info("GET /device is being called.");
        return ResponseEntity.ok(plantDeviceService.getAllDevices());
    }

    @GetMapping("/types")
    @Operation(summary = "Lade alle verfügbaren Gerätetypen")
    public ResponseEntity<List<DeviceTypeResponse>> fetchDeviceTypes() {
        log.info("GET /device is being called.");
        DeviceService service = factory.createService();
        return ResponseEntity.ok(service.getAllDeviceTypes());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lade ein Gerät")
    public ResponseEntity<Device> fetchDevice(@PathVariable Long id) {
        log.info("GET /device/{} is being called.", id);
        return ResponseEntity.ok(plantDeviceService.findById(id));
    }

    @GetMapping("/energyDistributionEvents")
    @Operation(summary = "Lade alle verfügbaren Energieverteilungsereignisse")
    public ResponseEntity<List<EnergyDistributionResponse>> fetchEnergyDistributionEvents(@RequestParam DeviceType deviceType) {
        log.info("GET /device/energyDistributionEvents is being called.");
        return ResponseEntity.ok(plantDeviceService.getAllEnergyDistributionEvents(deviceType));
    }

    @GetMapping("/group/{id}")
    @Operation(summary = "Lade alle validen Geräte für eine Gruppe")
    public ResponseEntity<List<Device>> fetchDevicesForGroup(@PathVariable Long id) {
        log.info("GET /device/group/{} is being called.", id);
        return ResponseEntity.ok(plantDeviceService.fetchValidDeviceGroups(id));
    }
}
