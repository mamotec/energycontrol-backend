package com.mamotec.energycontrolbackend.service.device;

import com.influxdb.client.QueryApi;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import com.mamotec.energycontrolbackend.client.InfluxClient;
import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.dao.RegisterMapping;
import com.mamotec.energycontrolbackend.utils.StringUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeviceDataService {

    private final InfluxClient influxClient;

    @Transactional
    public void writeDeviceData(Device device, String body, RegisterMapping mapping) {
        log.info("Saving data for device {} and measurement {}.", device.getUnitId(), mapping.getType());
        WriteApiBlocking writeApiBlocking = influxClient.getWriteApiBlocking();

        List<Point> points = new ArrayList<>();

        for (int j : StringUtils.toArray(body)) {
            Point p = Point.measurement(mapping.getType())
                    .addTag("device", device.getUnitId().toString())
                    .addField("value", j)
                    .time(Instant.now(), WritePrecision.NS);

            points.add(p);
        }

        writeApiBlocking.writePoints(points);
        log.info("Saved data for device {} and measurement {}.", device.getUnitId(), mapping.getType());
    }

    public void readLastDeviceData(Device device, RegisterMapping mapping) {
        log.info("Reading data for device {} and measurement {}.", device.getUnitId(), mapping.getType());
        QueryApi queryApi = influxClient.getQueryApi();

        String flux = String.format("from(bucket: %s)\n" +
                "  |> range(start: v.timeRangeStart, stop: v.timeRangeStop)\n" +
                "  |> filter(fn: (r) => r[\"_measurement\"] == %s)\n" +
                "  |> filter(fn: (r) => r[\"_field\"] == \"value\")\n" +
                "  |> aggregateWindow(every: v.windowPeriod, fn: sum, createEmpty: false)\n" +
                "  |> last()", influxClient.getInfluxBucket(), mapping.getType());

        List<FluxTable> tables = queryApi.query(flux);

        for (FluxTable table : tables) {
            List<FluxRecord> records = table.getRecords();

            for (FluxRecord record : records) {
                log.info("Record: {}", record);
            }
        }
    }
}
