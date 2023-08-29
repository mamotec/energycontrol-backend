package com.mamotec.energycontrolbackend.controller;

import com.mamotec.energycontrolbackend.domain.auth.AuthenticationRequest;
import com.mamotec.energycontrolbackend.domain.auth.AuthenticationResponse;
import com.mamotec.energycontrolbackend.domain.auth.RegisterRequest;
import com.mamotec.energycontrolbackend.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for authentication of the user.
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    @Operation(summary = "Registriere einen neuen Benutzer")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        log.info("POST /register is being called.");
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    @Operation(summary = "Authentifiziere einen Benutzer")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        log.info("POST /authenticate is being called.");
        return ResponseEntity.ok(service.authenticate(request));
    }
}
