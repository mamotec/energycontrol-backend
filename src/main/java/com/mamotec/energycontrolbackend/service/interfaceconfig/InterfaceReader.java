package com.mamotec.energycontrolbackend.service.interfaceconfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.dao.Interface;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class InterfaceReader {

    public Interface readInterface(String fileName) {
        try {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

            return mapper.readValue(new File(fileName), Interface.class);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
