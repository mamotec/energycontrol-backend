package com.mamotec.energycontrolbackend.factory;

import com.mamotec.energycontrolbackend.domain.interfaceconfig.InterfaceConfig;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.InterfaceType;
import com.mamotec.energycontrolbackend.repository.InterfaceConfigRepository;

public class InterfaceConfigFactory {

    public static InterfaceConfig aInterfaceConfig() {
        return InterfaceConfig.builder()
                .port("tty/s0")
                .protocolID(1)
                .type(InterfaceType.RS485)
                .build();
    }

    public static InterfaceConfig aInterfaceConfig(final InterfaceConfigRepository interfaceConfigRepository) {
        return interfaceConfigRepository.save(aInterfaceConfig());
    }
}
