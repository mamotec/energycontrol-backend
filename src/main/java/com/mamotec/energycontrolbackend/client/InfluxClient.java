package com.mamotec.energycontrolbackend.client;

import com.influxdb.client.*;
import com.mamotec.energycontrolbackend.exception.ExternalServiceNotAvailableException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@Slf4j
public class InfluxClient {

    @Value("${influx.url}")
    private String influxUrl;

    @Value("${influx.token}")
    private String influxToken;

    @Value("${influx.org}")
    private String influxOrg;

    @Value("${influx.bucket}")
    private String influxBucket;

    public InfluxDBClient getInfluxClient() {
        return InfluxDBClientFactory.create(influxUrl, influxToken.toCharArray(), influxOrg, influxBucket);
    }

    public WriteApiBlocking getWriteApiBlocking(){
        return getInfluxClient().getWriteApiBlocking();
    }

    public DeleteApi getDeleteApi(){
        return getInfluxClient().getDeleteApi();
    }

    public QueryApi getQueryApi(){
        return getInfluxClient().getQueryApi();
    }

    public void closeInfluxClient(InfluxDBClient influxDBClient) {
        getInfluxClient().close();
    }


    public boolean isInfluxDbAvailable(boolean withException) {
        InfluxDBClient influxClient = getInfluxClient();
        if (influxClient.ping()) {
            log.info("InfluxDB service available.");
            influxClient.close();
            return true;
        } else {
            if (withException) {
                throw new ExternalServiceNotAvailableException("InfluxDB service not available.");
            }
            log.error("InfluxDB service not available.");
            return false;
        }
    }
}
