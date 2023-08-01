package com.mamotec.energycontrolbackend.controller;

import com.mamotec.energycontrolbackend.domain.interfaceconfig.dao.Interface;
import com.mamotec.energycontrolbackend.service.interfaceconfig.InterfaceService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/interface")
@RequiredArgsConstructor
@Slf4j
@SecurityRequirement(name = "bearerAuth")
public class InterfaceController {

    private final InterfaceService interfaceService;

    @GetMapping()
    public ResponseEntity<List<Interface>> fetchInterfaces() {
        log.info("GET /interface is being called.");
        return ResponseEntity.ok(interfaceService.getAllInterfaces());
    }

}