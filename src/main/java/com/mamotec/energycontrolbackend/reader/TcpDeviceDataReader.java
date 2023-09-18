package com.mamotec.energycontrolbackend.reader;

import com.mamotec.energycontrolbackend.client.ModbusTCPClient;
import com.mamotec.energycontrolbackend.client.NodeRedClient;
import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.device.SerialDevice;
import com.mamotec.energycontrolbackend.domain.device.TcpDevice;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.InterfaceConfig;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.yaml.DeviceYaml;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.yaml.RegisterMapping;
import com.mamotec.energycontrolbackend.service.device.DeviceDataService;
import com.mamotec.energycontrolbackend.service.device.DeviceService;
import com.mamotec.energycontrolbackend.service.interfaceconfig.InterfaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class TcpDeviceDataReader {

    private final InterfaceService interfaceService;
    private final DeviceService deviceService;
    private final DeviceDataService deviceDataService;


    public void fetchDeviceData(InterfaceConfig config) {
        List<Device> devices = deviceService.getDevicesForInterfaceConfig(config.getId());

        log.info("READ - Found {} devices for interface {}.", devices.size(), config.getType());
        for (Device device : devices) {
            boolean noError = true;
            TcpDevice serialDevice = (SerialDevice) device;
            DeviceYaml i = interfaceService.getDeviceInformationForManufactureAndDeviceId(serialDevice.getManufacturerId(), serialDevice.getDeviceId());

            // Which register mapping to use?
            RegisterMapping mapping = i.getMapping()
                    .getPower();

            try {
                doFetchPerDevice(config, serialDevice, i, mapping);
            } catch (Exception e) {
                noError = false;
                log.error("READ - Error while fetching data for device {}.", serialDevice.getId(), e);
            }
            deviceDataService.markDeviceAsActive(device, noError);
        }
    }

    private void doFetchPerDevice(InterfaceConfig config, TcpDevice d, DeviceYaml deviceYaml, RegisterMapping mapping) throws IOException, InterruptedException {
        // Fetch data from node-red
        ModbusTCPClient client = new ModbusTCPClient(d.getHost(), Integer.parseInt(d.getPort()));

        // Save data to influxdb
        //deviceDataService.writeDeviceData(d, res, mapping);
    }
}
