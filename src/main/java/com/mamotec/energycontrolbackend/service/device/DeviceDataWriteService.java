package com.mamotec.energycontrolbackend.service.device;

import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.mamotec.energycontrolbackend.client.InfluxClient;
import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.utils.StringUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;

import static java.lang.String.format;

@Slf4j
@RequiredArgsConstructor
@Component
public class DeviceDataWriteService {

    private final InfluxClient influxClient;

    @Transactional
    public void writeDeviceData(Device device, String body, String measurement) {
        if (body == null) {
            return;
        }

        // InfluxDB
        log.info("Saving data for device {} and measurement {}.", device.getId(), measurement);
        WriteApiBlocking writeApiBlocking = influxClient.getWriteApiBlocking();

        Point p = Point.measurement(measurement)
                .addTag("device", device.getId()
                        .toString())
                .addTag("deviceType", device.getDeviceType().toString())
                .time(Instant.now(), WritePrecision.NS);

        double[] array = StringUtils.toArray(body);
        int counter = 0;
        int sum = 0;
        for (double j : array) {
            counter++;
            p.addField(format("value_%s", counter), j);
            sum += j;
        }

        p.addField("sum", sum);

        writeApiBlocking.writePoint(p);
        log.info("Saved data for device {} and measurement {}.", device.getId(), measurement);
    }
}
