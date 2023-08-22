package com.mamotec.energycontrolbackend.factory;

import com.mamotec.energycontrolbackend.domain.interfaceconfig.InterfaceConfig;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.InterfaceType;
import com.mamotec.energycontrolbackend.repository.InterfaceConfigRepository;

public class InterfaceConfigFactory {

    public static InterfaceConfig aInterfaceConfig() {
        InterfaceConfig c = new InterfaceConfig();
        c.setDescription("Port 1 - RS485");
        c.setType(InterfaceType.RS485);

        return c;
    }

    public static InterfaceConfig aInterfaceConfig(final InterfaceConfigRepository interfaceConfigRepository) {
        return interfaceConfigRepository.save(aInterfaceConfig());
    }
}
