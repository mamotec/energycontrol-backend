package com.mamotec.energycontrolbackend.service;

import com.mamotec.energycontrolbackend.domain.device.Interface;
import com.mamotec.energycontrolbackend.domain.device.InterfaceRequest;
import com.mamotec.energycontrolbackend.domain.device.InterfaceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
public class InterfaceService {
    public InterfaceResponse create(InterfaceRequest interfaceRequest) {
        var toCreate = Interface.builder().deviceCategory(interfaceRequest.getDeviceCategory());
        return null;
    }
}
