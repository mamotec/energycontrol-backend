package com.mamotec.energycontrolbackend.domain.device.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeviceLinkRequest {

    private List<Long> deviceIds;

}
