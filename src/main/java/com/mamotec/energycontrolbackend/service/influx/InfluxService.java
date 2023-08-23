package com.mamotec.energycontrolbackend.service.influx;

import com.influxdb.client.DeleteApi;
import com.mamotec.energycontrolbackend.client.InfluxClient;
import com.mamotec.energycontrolbackend.domain.device.Device;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class InfluxService {

    private final InfluxClient client;


    public void deleteAllDataPointsForDevice(Device device) {
        log.debug("Deleting all data points for device {}.", device.getUnitId());

        DeleteApi api = client.getDeleteApi();

        // TODO: 2021-10-06: Delete all data points for device

    }
}
