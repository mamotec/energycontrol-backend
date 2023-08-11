package com.mamotec.energycontrolbackend.client;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.QueryApi;
import com.influxdb.client.WriteApiBlocking;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
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

    public QueryApi getQueryApi(){
        return getInfluxClient().getQueryApi();
    }

    public void closeInfluxClient(InfluxDBClient influxDBClient) {
        getInfluxClient().close();
    }
}
