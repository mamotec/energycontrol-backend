package com.mamotec.energycontrolbackend.client;

import com.mamotec.energycontrolbackend.exception.ExternalServiceNotAvailableException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class NodeRedClient {

    private static final String DEVICE_DATA_URL = "/device/%d/data";
    private static final String DEVICE_CHECK_URL = "/device/%d/check";

    @Value("${node-red.url}")
    private String nodeRedUrl;


    public Object fetchDeviceData(@NotNull final long deviceId) {
        log.info("Fetching device {} data... using node-red url: {}", deviceId, nodeRedUrl);
        isServiceAvailable(true);

        HttpClient httpClient = HttpClients.createDefault();

        HttpGet req = new HttpGet(nodeRedUrl + String.format(DEVICE_DATA_URL, deviceId));

        try {
            HttpResponse res = httpClient.execute(req);

            int statusCode = res.getStatusLine()
                    .getStatusCode();
            HttpEntity entity = res.getEntity();
            String responseBody = EntityUtils.toString(entity);

            return null;
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
    public void checkDevice(final long deviceId) {
        log.info("Search for device {} ... using node-red url: {}", deviceId, nodeRedUrl);

        isServiceAvailable(true);

        HttpClient httpClient = HttpClients.createDefault();

        HttpGet req = new HttpGet(nodeRedUrl + String.format(DEVICE_CHECK_URL, deviceId));

        try {
            HttpResponse res = httpClient.execute(req);

            int statusCode = res.getStatusLine()
                    .getStatusCode();
            HttpEntity entity = res.getEntity();
            String responseBody = EntityUtils.toString(entity);

        } catch (IOException e) {
            log.error("Error while fetching device data from node-red.");
            e.printStackTrace();
        }

    }
}
