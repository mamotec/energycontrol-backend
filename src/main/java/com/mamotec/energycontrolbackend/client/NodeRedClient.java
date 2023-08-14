package com.mamotec.energycontrolbackend.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.InterfaceConfig;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.dao.Interface;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.dao.RegisterMapping;
import com.mamotec.energycontrolbackend.exception.ExternalServiceNotAvailableException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class NodeRedClient {

    private static final String DEVICE_URL = "/device";

    private final java.net.http.HttpClient httpClient = java.net.http.HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .build();

    private final ObjectMapper objectMapper;

    @Value("${node-red.url}")
    private String nodeRedUrl;


    public String fetchDeviceData(Interface i, InterfaceConfig config, final long deviceUnitId, RegisterMapping mapping) throws IOException, InterruptedException {
        log.info("Fetching device {} data... using node-red url: {}", deviceUnitId, nodeRedUrl);
        isNodeRedAvailable(true);

        Map<String, String> values;
        values = DeviceRequestBodyBuilder.buildPostWithMapping(mapping);
        DeviceRequestBodyBuilder.buildConnectionPost(i, config, deviceUnitId, values);

        String requestBody = objectMapper.writeValueAsString(values);

        HttpRequest request = HttpRequest.newBuilder()
                .timeout(java.time.Duration.ofSeconds(5))
                .uri(URI.create(nodeRedUrl + String.format(DEVICE_URL, deviceUnitId)))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request,
                    java.net.http.HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                log.error("Error while fetching device data from node-red. Status code: {}", response.statusCode());
                throw new ExternalServiceNotAvailableException("NodeRED service not available.");
            }

            return response.body();

        } catch (IOException e) {
            log.error("Error while fetching device data from node-red.");
            e.printStackTrace();
        }

        return null;
    }

    public boolean isNodeRedAvailable(boolean withException) {
        HttpRequest request = HttpRequest.newBuilder()
                .timeout(java.time.Duration.ofSeconds(5))
                .uri(URI.create(nodeRedUrl))
                .GET()
                .build();

        try {
            HttpResponse<String> res = httpClient.send(request, java.net.http.HttpResponse.BodyHandlers.ofString());

            int statusCode = res.statusCode();

            if (statusCode != 200) {
                log.error("NodeRED status not 200. Status code: {}", statusCode);
                return false;
            }
            log.info("NodeRED service available. Status code: {}", statusCode);
            return true;

        } catch (IOException | InterruptedException e) {
            if (withException) {
                throw new ExternalServiceNotAvailableException("NodeRED service not available.", e);
            }
            return false;
        }
    }

    /**
     * Should check if there is a device with the given id.
     */
    public boolean checkDevice(final long deviceId, InterfaceConfig config, Interface anInterface) {
        log.info("Search for device {} ... using node-red url: {}", deviceId, nodeRedUrl);

        isNodeRedAvailable(true);

        RegisterMapping mapping = anInterface.getMapping()
                .getPower();

        try {
            String res = fetchDeviceData(anInterface, config, deviceId, mapping);

            return Strings.isNotBlank(res);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
