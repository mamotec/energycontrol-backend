package com.mamotec.energycontrolbackend.utils;

import com.mamotec.energycontrolbackend.domain.interfaceconfig.InterfaceConfig;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.InterfaceType;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.yaml.DeviceYaml;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.yaml.RegisterMapping;

import java.util.HashMap;
import java.util.Map;

public final class DeviceRequestBodyBuilder {

    private DeviceRequestBodyBuilder() {
        throw new IllegalStateException("Utility class");
    }

    public static void buildConnectionPost(DeviceYaml i, InterfaceConfig config, long unitId, Map<String, String> requestBody) {

        if (config.getType().equals(InterfaceType.RS485)) {
            // Modbus-Server - SERIAL
            requestBody.put("baudrate", String.valueOf(i.getConnection().getBaudRate()));
            requestBody.put("connectorType", "SERIAL");
        } else if (config.getType().equals(InterfaceType.TCP)) {
            // Modbus-Server - TCP
            requestBody.put("connectorType", "TCP");
        }

        // Modbus-Client
        requestBody.put("unitid", String.valueOf(unitId));

    }

    public static Map<String, String> buildPostWithMapping(RegisterMapping mapping) {

        Map<String, String> requestBody = new HashMap<>();

        requestBody.put("fc", String.valueOf(mapping.getFc().getCode()));
        requestBody.put("address", String.valueOf(mapping.getRegister().get(0)));
        requestBody.put("quantity", String.valueOf(mapping.getRegister().size()));

        return requestBody;
    }

}
