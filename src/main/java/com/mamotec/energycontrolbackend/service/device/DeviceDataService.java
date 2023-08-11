package com.mamotec.energycontrolbackend.service.device;

import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.mamotec.energycontrolbackend.client.InfluxClient;
import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.utils.StringUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class DeviceDataService {

    private final InfluxClient influxClient;

    @Transactional
    public void saveDeviceData(Device device, String body) {
        WriteApiBlocking writeApiBlocking = influxClient.getWriteApiBlocking();

        int[] array = StringUtils.toArray(body);
        int i = new Random().nextInt(1000000);

        List<Point> points = new ArrayList<>();

        for (int j : array) {
            Point p = Point.measurement("power")
                    .addTag("device", device.getUnitId().toString())
                    .addField("value", j)
                    .time(Instant.now(), WritePrecision.NS);

            points.add(p);

        }

        writeApiBlocking.writePoints(points);



    }
}
