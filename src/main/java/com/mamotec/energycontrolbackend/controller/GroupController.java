package com.mamotec.energycontrolbackend.controller;

import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.group.DeviceGroup;
import com.mamotec.energycontrolbackend.service.group.GroupService;
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
public class GroupController {

    private final GroupService groupService;

    @PostMapping
    @Operation(summary = "Erstelle eine neue Gruppe")
    public ResponseEntity<DeviceGroup> createGroup(@RequestBody DeviceGroup deviceGroup) {
        log.info("POST /group is being called.");
        return ResponseEntity.ok(groupService.save(deviceGroup));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Lösche eine Gruppe")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long id) {
        log.info("DELETE /group/{} is being called.", id);
        groupService.deleteGroup(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/link")
    @Operation(summary = "Füge ein oder mehrere Device zu einer Gruppe hinzu")
    public ResponseEntity<Void> addDeviceToGroup(@PathVariable Long id, @RequestBody List<Device> devices) {
        log.info("PUT /group/{}/link is being called.", id);
        groupService.addDevicesToGroup(id, devices);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/unlink")
    @Operation(summary = "Lösche ein oder mehrere Device aus einer Gruppe")
    public ResponseEntity<Void> deleteDeviceFromGroup(@PathVariable Long id, @RequestBody List<Device> devices) {
        log.info("DELETE /group/{}/unlink is being called.", id);
        groupService.deleteDevicesFromGroup(id, devices);
        return ResponseEntity.ok().build();
    }
}
