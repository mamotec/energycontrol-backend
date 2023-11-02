package com.mamotec.energycontrolbackend.service.device;

import com.influxdb.client.QueryApi;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import com.mamotec.energycontrolbackend.client.InfluxClient;
import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.repository.DeviceRepository;
import com.mamotec.energycontrolbackend.utils.StringUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeviceDataReadService {

    private final InfluxClient influxClient;

    public long readLastDeviceData(List<Long> deviceIds, String measurement) {
        QueryApi queryApi = influxClient.getQueryApi();
        StringBuilder deviceString = new StringBuilder("|> filter(fn: (r) => ");
        if (deviceIds.size() > 1) {
            for (Long deviceId : deviceIds) {
                deviceString.append(format("or r[\"device\"] == \"%s\" ", deviceId));
            }
        } else if (deviceIds.size() == 1) {
            deviceString.append(format("r[\"device\"] == \"%s\" ", deviceIds.get(0)));
        } else {
            return 0;
        }

        deviceString.append(")");

        String flux = format(new StringBuilder().append("from(bucket: \"%s\")")
                .append("  |> range(start: -1h)")
                .append("  |> filter(fn: (r) => r._measurement == \"%s\")")
                .append(deviceString)
                .append("  |> filter(fn: (r) => r[\"_field\"] == \"sum\")")
                .append("  |> last()")
                .append("  |> yield(name: \"last\")")
                .toString(), influxClient.getInfluxBucket(), measurement);

        List<FluxTable> tables;
        try {
            tables = queryApi.query(flux);
        } catch (Exception e) {
            log.error("Error while reading data from influxdb.", e);
            return 0;
        }

        for (FluxTable table : tables) {
            List<FluxRecord> records = table.getRecords();

            for (FluxRecord record : records) {
                return (long) record.getValue();
            }
        }

        return 0;

    }
}
