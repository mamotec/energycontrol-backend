package com.mamotec.energycontrolbackend.domain.device;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InterfaceRequest {
    private DeviceCategory deviceCategory;
}
