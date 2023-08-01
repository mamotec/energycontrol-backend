package com.mamotec.energycontrolbackend.client;

import com.mamotec.energycontrolbackend.domain.interfaceconfig.dao.Interface;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.dao.RegisterMapping;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
public final class DeviceRequestBodyBuilder {

    public static Map<String, String> buildSerialPost(Interface i, long unitId) {
        HashMap<String, String> requestBody = new HashMap<>();

        // Modbus-Server
        requestBody.put("baudrate", String.valueOf(i.getConnection().getBaudRate()));
        requestBody.put("parity", String.valueOf(i.getConnection().getParity()));
        requestBody.put("stopbits", String.valueOf(i.getConnection().getStopBits()));
        requestBody.put("databits", String.valueOf(i.getConnection().getDataBits()));
        requestBody.put("serialPort", "/dev/ttyS0");

        // Modbus-Client
        requestBody.put("unitid", String.valueOf(unitId));

        return requestBody;
    }

    public static void buildPostWithMapping(RegisterMapping mapping, Map<String, String> requestBody) {

        requestBody.put("fc", String.valueOf(mapping.getFc().getCode()));
        requestBody.put("address", String.valueOf(mapping.getRegister().get(0)));
        requestBody.put("quantity", String.valueOf(mapping.getRegister().size()));

    }

}
