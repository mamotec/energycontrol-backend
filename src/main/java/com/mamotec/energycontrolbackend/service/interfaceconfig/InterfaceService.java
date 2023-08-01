package com.mamotec.energycontrolbackend.service.interfaceconfig;

import com.mamotec.energycontrolbackend.domain.interfaceconfig.InterfaceConfig;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.dao.Interface;
import com.mamotec.energycontrolbackend.service.CrudOperations;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.mamotec.energycontrolbackend.utils.FolderUtils.getResourcesInFolder;

@Service
@RequiredArgsConstructor
public class InterfaceService {

    private final InterfaceReader reader;

    public Interface getInterfaceById(String interfaceId) {
        return reader.readInterface(interfaceId);
    }

    public List<Interface> getAllInterfaces() {
        List<URL> resourceFiles = new ArrayList<>();
        try {
            resourceFiles = getResourcesInFolder("interface");

        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Interface> interfaces = new ArrayList<>();

        for (URL resourceFile : resourceFiles) {
            Interface i = getInterfaceById(resourceFile.getFile());
            interfaces.add(i);
        }

        return interfaces;

    }


}
