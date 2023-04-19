package com.mamotec.energycontrolbackend.controller;

import com.mamotec.energycontrolbackend.domain.device.InterfaceRequest;
import com.mamotec.energycontrolbackend.domain.device.InterfaceResponse;
import com.mamotec.energycontrolbackend.service.InterfaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Conntroller for interface operations
 */
@RestController
@RequestMapping("/interface")
@RequiredArgsConstructor
@Slf4j
public class InterfaceController {

    private final InterfaceService service;

    @PostMapping
    public ResponseEntity<InterfaceResponse> create(@RequestBody InterfaceRequest interfaceRequest) {
        log.info("POST /interface is being called.");
        return ResponseEntity.ok(service.create(interfaceRequest));

    }
}
