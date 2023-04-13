package com.mamotec.energycontrolbackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/echt")
    public String test() {
        return "Ouida";
    }
}
