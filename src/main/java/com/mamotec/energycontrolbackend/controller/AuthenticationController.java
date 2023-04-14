package com.mamotec.energycontrolbackend.controller;

import com.mamotec.energycontrolbackend.domain.auth.AuthenticationRequest;
import com.mamotec.energycontrolbackend.domain.auth.AuthenticationResponse;
import com.mamotec.energycontrolbackend.domain.auth.RegisterRequest;
import com.mamotec.energycontrolbackend.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class AuthenticationController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        logger.info("POST /register is being called.");
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        logger.info("POST /authenticate is being called.");
        return ResponseEntity.ok(service.authenticate(request));
    }
}