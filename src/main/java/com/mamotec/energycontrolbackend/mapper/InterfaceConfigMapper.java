package com.mamotec.energycontrolbackend.mapper;

import com.mamotec.energycontrolbackend.domain.device.InterfaceConfig;
import com.mamotec.energycontrolbackend.domain.device.dao.InterfaceConfigRequest;
import com.mamotec.energycontrolbackend.domain.device.dao.InterfaceConfigResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InterfaceConfigMapper {

    InterfaceConfig map(InterfaceConfigRequest request);

    InterfaceConfigResponse map(InterfaceConfig request);
}
