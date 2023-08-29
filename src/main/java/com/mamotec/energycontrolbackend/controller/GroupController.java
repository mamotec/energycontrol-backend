package com.mamotec.energycontrolbackend.controller;

import com.mamotec.energycontrolbackend.domain.group.Group;
import com.mamotec.energycontrolbackend.service.group.GroupService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
@Slf4j
@SecurityRequirement(name = "bearerAuth")
public class GroupController {

    private final GroupService service;

    @PostMapping
    public ResponseEntity<Group> createGroup(@RequestBody Group group) {
        log.info("POST /group is being called.");
        return ResponseEntity.ok(service.save(group));
    }
}
