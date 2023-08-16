package com.mamotec.energycontrolbackend.domain.device.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeviceScanDao {

    List<DeviceCreateRequest> alreadyExistingDevices = new ArrayList<>();
    List<DeviceCreateRequest> newDevices = new ArrayList<>();
}
