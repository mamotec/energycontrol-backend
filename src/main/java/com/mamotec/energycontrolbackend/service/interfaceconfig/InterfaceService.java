package com.mamotec.energycontrolbackend.service.interfaceconfig;

import com.mamotec.energycontrolbackend.domain.interfaceconfig.dao.Interface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InterfaceService {

    private final InterfaceReader reader;

    public List<Interface> getAllInterfaces() {
        InputStream is = getClass().getResourceAsStream("/interfaceConfig.yaml");

        return reader.readInterface(is);

    }

    public Interface getInterfaceByProtocolId(long protocolId) {
        List<Interface> interfaces = getAllInterfaces();
        for (Interface i : interfaces) {
            if (i.getMetaData()
                    .getProtocolId() == protocolId) {
                return i;
            }
        }
        return null;
    }


}
