package com.mamotec.energycontrolbackend.service.device;

import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.mamotec.energycontrolbackend.client.InfluxClient;
import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.dao.RegisterMapping;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeviceDataService {

    private final InfluxClient influxClient;

    @Transactional
    public void saveDeviceData(Device device, RegisterMapping mapping, String body) {
        WriteApiBlocking writeApiBlocking = influxClient.getWriteApiBlocking();

        Point p = Point.measurement("power")
                .addTag("device", device.getUnitId().toString())
                .addField("value", body)
                .time(System.currentTimeMillis(), WritePrecision.MS);

        writeApiBlocking.writePoint(p);

    }
}
