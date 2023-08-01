package com.mamotec.energycontrolbackend.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.dao.Interface;
import com.mamotec.energycontrolbackend.exception.ExternalServiceNotAvailableException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class NodeRedClient {

    private static final String DEVICE_URL = "/device/%d";

    private final java.net.http.HttpClient httpClient = java.net.http.HttpClient.newHttpClient();

    private final ObjectMapper objectMapper;

    @Value("${node-red.url}")
    private String nodeRedUrl;


    public Object fetchDeviceData(Interface i, @NotNull final long unitId) throws IOException, InterruptedException {
        log.info("Fetching device {} data... using node-red url: {}", unitId, nodeRedUrl);
        isServiceAvailable(true);

        Map<String, String> values = DeviceRequestBodyBuilder.buildSerialPost(i, unitId);
        DeviceRequestBodyBuilder.buildPostWithMapping(i.getMapping()
                .getPower(), values);

        String requestBody = objectMapper.writeValueAsString(values);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(nodeRedUrl + String.format(DEVICE_URL, unitId)))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();


        try {
            java.net.http.HttpResponse<String> response = httpClient.send(request,
                    java.net.http.HttpResponse.BodyHandlers.ofString());

            return response.body();
        } catch (IOException e) {
            log.error("Error while fetching device data from node-red.");
            e.printStackTrace();
        }

        return null;
    }

    public boolean isServiceAvailable(boolean withException) {
        HttpClient httpClient = HttpClients.createDefault();

        HttpGet req = new HttpGet(nodeRedUrl);

        try {
            HttpResponse res = httpClient.execute(req);


            int statusCode = res.getStatusLine()
                    .getStatusCode();

            if (statusCode != 200) {
                log.error("NodeRED status not 200. Status code: {}", statusCode);
                return false;
            }
            log.info("NodeRED service available. Status code: {}", statusCode);
            return true;

        } catch (IOException e) {
            if (withException) {
                throw new ExternalServiceNotAvailableException("NodeRED service not available.");
            }
            return false;
        }
    }

    /**
     * Should check if there is a device with the given id.
     */
    public void checkDevice(final long deviceId, Interface anInterface) {
        log.info("Search for device {} ... using node-red url: {}", deviceId, nodeRedUrl);

        isServiceAvailable(true);
    }
}
