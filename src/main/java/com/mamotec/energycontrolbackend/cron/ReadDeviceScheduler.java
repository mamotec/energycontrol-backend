package com.mamotec.energycontrolbackend.cron;

import com.ghgande.j2mod.modbus.facade.ModbusSerialMaster;
import com.ghgande.j2mod.modbus.procimg.InputRegister;
import com.ghgande.j2mod.modbus.util.SerialParameters;
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

    @Scheduled(cron = "*/10 * * * * *")
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
        SerialParameters params = new SerialParameters();
        params.setPortName("/dev/ttyS0");
        params.setBaudRate(9600);
        params.setDatabits(8);
        params.setParity("None");
        params.setStopbits(1);
        params.setEncoding("rtu");

        ModbusSerialMaster modbusClient = new ModbusSerialMaster(params); // Serieller Port

        try {

            modbusClient.connect();
            int startAddress = 672; // Startadresse
            int quantity = 2; // Anzahl von Registern

            InputRegister[] inputRegisters = modbusClient.readInputRegisters(2, startAddress, quantity);
            for (int i = 0; i < inputRegisters.length; i++) {
                System.out.println("Register " + (startAddress + i) + ": " + inputRegisters[i]);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            modbusClient.disconnect();
        }

        // Save data to influxdb
        deviceDataService.writeDeviceData(d, "res", mapping);
    }


}
