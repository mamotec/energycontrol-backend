package com.mamotec.energycontrolbackend.controller;

import com.mamotec.energycontrolbackend.domain.device.dao.DeviceLinkRequest;
import com.mamotec.energycontrolbackend.domain.group.DeviceGroup;
import com.mamotec.energycontrolbackend.domain.group.dao.DeviceGroupCreate;
import com.mamotec.energycontrolbackend.domain.group.dao.DeviceGroupRepresentation;
import com.mamotec.energycontrolbackend.domain.group.dao.DeviceGroupUpdate;
import com.mamotec.energycontrolbackend.domain.group.dao.home.HomeDataRepresentation;
import com.mamotec.energycontrolbackend.mapper.DeviceGroupMapper;
import com.mamotec.energycontrolbackend.service.group.AggregateDeviceGroupDataService;
import com.mamotec.energycontrolbackend.service.group.DeviceGroupService;
import com.mamotec.energycontrolbackend.service.group.home.HomeAggregateDeviceGroupDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
@Slf4j
@SecurityRequirement(name = "bearerAuth")
public class DeviceGroupController {

    private final DeviceGroupService deviceGroupService;
    private final AggregateDeviceGroupDataService aggregateDeviceGroupDataService;
    private final HomeAggregateDeviceGroupDataService homeAggregateDeviceGroupDataService;
    private final DeviceGroupMapper deviceGroupMapper;

    @GetMapping
    @Operation(summary = "Liefere alle Gruppen")
    public ResponseEntity<List<DeviceGroup>> getAllGroups() {
        log.info("GET /group is being called.");
        return ResponseEntity.ok(deviceGroupService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Liefere eine Gruppe")
    public ResponseEntity<DeviceGroup> getGroup(@PathVariable Long id) {
        log.info("GET /group/{} is being called.", id);
        return ResponseEntity.ok(deviceGroupService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Erstelle eine neue Gruppe")
    public ResponseEntity<DeviceGroup> createGroup(@RequestBody DeviceGroupCreate deviceGroup) {
        log.info("POST /group is being called.");
        return ResponseEntity.ok(deviceGroupService.save(deviceGroupMapper.map(deviceGroup)));
    }

    @PutMapping
    @Operation(summary = "Aktualisiere eine Gruppe")
    public ResponseEntity<DeviceGroup> updateGroup(@RequestBody DeviceGroupUpdate deviceGroup) {
        log.info("PUT /group is being called.");
        return ResponseEntity.ok(deviceGroupService.save(deviceGroupMapper.map(deviceGroup)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Lösche eine Gruppe")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long id) {
        log.info("DELETE /group/{} is being called.", id);
        deviceGroupService.deleteGroup(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/link")
    @Operation(summary = "Füge ein oder mehrere Device zu einer Gruppe hinzu")
    public ResponseEntity<Void> addDeviceToGroup(@PathVariable Long id, @RequestBody DeviceLinkRequest request) {
        log.info("PUT /group/{}/link is being called.", id);
        deviceGroupService.addDevicesToGroup(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/unlink")
    @Operation(summary = "Lösche referenz zur Gruppe für ein oder mehrere Geräte")
    public ResponseEntity<Void> deleteDeviceFromGroup(@RequestBody DeviceLinkRequest request) {
        log.info("DELETE /group/unlink is being called.");
        deviceGroupService.deleteDevicesFromGroup(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/data/{id}")
    @Operation(summary = "Liefere die Daten für eine Gruppe")
    public ResponseEntity<DeviceGroupRepresentation> fetchDeviceGroupData(@PathVariable Long id) {
        log.info("GET /group/data is being called.");
        return ResponseEntity.ok(aggregateDeviceGroupDataService.aggregate(id));
    }

    @GetMapping("/home/dashboard")
    public ResponseEntity<HomeDataRepresentation> fetchHomeDashboard() {
        log.info("GET /group/home/dashboard is being called.");
        return ResponseEntity.ok(homeAggregateDeviceGroupDataService.aggregate());
    }


}
