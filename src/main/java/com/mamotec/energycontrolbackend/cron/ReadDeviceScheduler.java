package com.mamotec.energycontrolbackend.cron;

import com.mamotec.energycontrolbackend.client.NodeRedClient;
import com.mamotec.energycontrolbackend.domain.device.Device;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.InterfaceConfig;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.yaml.DeviceYaml;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.yaml.RegisterMapping;
import com.mamotec.energycontrolbackend.service.device.DeviceDataService;
import com.mamotec.energycontrolbackend.service.device.DeviceService;
import com.mamotec.energycontrolbackend.service.interfaceconfig.InterfaceConfigService;
import com.mamotec.energycontrolbackend.service.interfaceconfig.InterfaceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.wimpi.modbus.Modbus;
import net.wimpi.modbus.io.ModbusSerialTransaction;
import net.wimpi.modbus.msg.ReadInputRegistersRequest;
import net.wimpi.modbus.msg.ReadInputRegistersResponse;
import net.wimpi.modbus.net.SerialConnection;
import net.wimpi.modbus.procimg.InputRegister;
import net.wimpi.modbus.util.SerialParameters;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReadDeviceScheduler {

    private final InterfaceConfigService interfaceConfigService;
    private final InterfaceService interfaceService;
    private final DeviceService deviceService;
    private final NodeRedClient nodeRedClient;
    private final DeviceDataService deviceDataService;

    @Scheduled(cron = "*/59 * * * * *")
    @Transactional
    public void fetchDeviceData() {
        List<InterfaceConfig> configs = interfaceConfigService.findAll();
        log.info("READ - Found {} interfaces in repository.", configs.size());

        for (InterfaceConfig config : configs) {

            List<Device> devices = deviceService.getDevicesForInterfaceConfig(config.getId());

            log.info("READ - Found {} devices for interface {}.", devices.size(), config.getType());
            for (Device device : devices) {
                boolean noError = true;
                DeviceYaml i = interfaceService.getDeviceInformationForManufactureAndDeviceId(device.getManufacturerId(), device.getDeviceId());

                // Which register mapping to use?
                RegisterMapping mapping = i.getMapping()
                        .getPower();

                try {
                    doFetchPerDevice(config, device, i, mapping);
                } catch (Exception e) {
                    noError = false;
                    log.error("READ - Error while fetching data for device {}.", device.getUnitId(), e);
                }
                deviceDataService.markDeviceAsActive(device, noError);
            }
        }
    }

    private void doFetchPerDevice(InterfaceConfig config, Device d, DeviceYaml deviceYaml, RegisterMapping mapping) throws IOException, InterruptedException {
        // Fetch data from node-red
        // String res = nodeRedClient.fetchDeviceData(deviceYaml, config, d.getUnitId(), mapping);
        try {
            // Serielle Verbindungsparameter
            SerialParameters serialParams = new SerialParameters();
            serialParams.setPortName("/dev/ttyS0"); // Anpassen des seriellen Ports
            serialParams.setBaudRate(9600);
            serialParams.setDatabits(8);
            serialParams.setParity("None");
            serialParams.setStopbits(1);
            serialParams.setEncoding(Modbus.SERIAL_ENCODING_RTU);
            serialParams.setEcho(false);

            // Serielle Verbindung herstellen
            SerialConnection connection = new SerialConnection(serialParams);
            connection.open();

            // Modbus-Anfrage erstellen
            ReadInputRegistersRequest req = new ReadInputRegistersRequest(mapping.getRegister().get(0), mapping.getRegister().size()); // Startadresse: 672, Anzahl: 2
            req.setUnitID(d.getUnitId()); // Unit-ID des Modbus-Slave-Geräts

            // Modbus-Transaktion ausführen
            ModbusSerialTransaction transaction = new ModbusSerialTransaction(connection);
            transaction.setRequest(req);
            transaction.execute();

            // Antwort verarbeiten
            ReadInputRegistersResponse res = (ReadInputRegistersResponse) transaction.getResponse();
            if (res != null) {
                InputRegister[] data = res.getRegisters().clone();
                for (InputRegister value : data) {
                    System.out.println("Value: " + value);
                }
            }

            // Serielle Verbindung schließen
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        // Save data to influxdb
        deviceDataService.writeDeviceData(d, "res", mapping);
    }


}
