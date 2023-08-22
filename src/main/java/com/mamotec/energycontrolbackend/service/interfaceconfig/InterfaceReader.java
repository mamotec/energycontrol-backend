package com.mamotec.energycontrolbackend.service.interfaceconfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.yaml.InterfaceYaml;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.yaml.InterfacesYaml;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class InterfaceReader {

    public List<InterfaceYaml> readInterface(InputStream is) {
        try {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

            InterfacesYaml config = mapper.readValue(is, InterfacesYaml.class);

            return config.getInterfaces();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
