package com.mamotec.energycontrolbackend.service.device;

import com.influxdb.client.QueryApi;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import com.mamotec.energycontrolbackend.client.InfluxClient;
import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.yaml.RegisterMapping;
import com.mamotec.energycontrolbackend.repository.DeviceRepository;
import com.mamotec.energycontrolbackend.utils.StringUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeviceDataService {

    private final InfluxClient influxClient;
    private final DeviceRepository deviceRepository;

    @Transactional
    public void writeDeviceData(Device device, String body, RegisterMapping mapping) {
        if (body == null) {
            return;
        }

        // InfluxDB
        log.info("Saving data for device {} and measurement {}.", device.getUnitId(), mapping.getType());
        WriteApiBlocking writeApiBlocking = influxClient.getWriteApiBlocking();

        Point p = Point.measurement(mapping.getType())
                .addTag("device", device.getId()
                        .toString())
                .addTag("deviceType", device.getDeviceType().toString())
                .time(Instant.now(), WritePrecision.NS);

        int[] array = StringUtils.toArray(body);
        int counter = 0;
        int sum = 0;
        for (int j : array) {
            counter++;
            p.addField(String.format("value_%s", counter), j);
            sum += j;
        }

        p.addField("sum", sum);

        writeApiBlocking.writePoint(p);
        log.info("Saved data for device {} and measurement {}.", device.getUnitId(), mapping.getType());
    }

    public void markDeviceAsActive(Device device, boolean active) {
        deviceRepository.markDeviceAsActive(device.getId(), active);
    }

    public long readLastDeviceData(Device device, String measurement) {
        log.info("Reading data for device {} and measurement {}.", device.getUnitId(), measurement);
        QueryApi queryApi = influxClient.getQueryApi();

        String flux = String.format("from(bucket: \"%s\")" +
                "  |> range(start: -1h)" +
                "  |> filter(fn: (r) => r._measurement == \"%s\")" +
                "  |> filter(fn: (r) => r[\"_field\"] == \"sum\")" +
                "  |> last()" +
                "  |> yield(name: \"last\")", influxClient.getInfluxBucket(), measurement);

        List<FluxTable> tables = queryApi.query(flux);

        for (FluxTable table : tables) {
            List<FluxRecord> records = table.getRecords();

            for (FluxRecord record : records) {
                return (long) record.getValue();
            }
        }

        return 0;

    }
}
