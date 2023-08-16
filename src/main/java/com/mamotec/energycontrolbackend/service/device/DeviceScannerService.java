package com.mamotec.energycontrolbackend.service.device;

import com.mamotec.energycontrolbackend.client.NodeRedClient;
import com.mamotec.energycontrolbackend.domain.device.dao.DeviceCreateRequest;
import com.mamotec.energycontrolbackend.domain.device.dao.DeviceScanDao;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.InterfaceConfig;
import com.mamotec.energycontrolbackend.domain.interfaceconfig.dao.Interface;
import com.mamotec.energycontrolbackend.repository.DeviceRepository;
import com.mamotec.energycontrolbackend.service.interfaceconfig.InterfaceConfigService;
import com.mamotec.energycontrolbackend.service.interfaceconfig.InterfaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.wimpi.modbus.io.ModbusSerialTransaction;
import net.wimpi.modbus.msg.ReadInputRegistersRequest;
import net.wimpi.modbus.msg.ReadInputRegistersResponse;
import net.wimpi.modbus.net.SerialConnection;
import net.wimpi.modbus.util.SerialParameters;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeviceScannerService {

    private final InterfaceConfigService interfaceConfigService;

    private final InterfaceService interfaceService;

    private final NodeRedClient nodeRedClient;

    private final DeviceRepository deviceRepository;

    private final DeviceService deviceService;

    public DeviceScanDao deviceScan() {
        List<InterfaceConfig> interfaces = interfaceConfigService.findAll();

        for (InterfaceConfig interfaceConfig : interfaces) {
            log.info("Scanning interface {}...", interfaceConfig.getType());
            return scan(interfaceConfig);
        }

        // TODO: Throw exception if no interface is available
        return null;
    }

    private DeviceScanDao scan(InterfaceConfig config) {
        Interface anInterface = interfaceService.getInterfaceByProtocolId(config.getProtocolId());
        DeviceScanDao dao = new DeviceScanDao();

        for (int slaveAddress = 1; slaveAddress <= config.getType()
                .getMaxDevices(); slaveAddress++) {
            searchModbusDevices();
            //boolean isDeviceAvailable = nodeRedClient.checkDevice(slaveAddress, config, anInterface);
            if (false) {
                if (deviceRepository.existsByUnitIdAndInterfaceConfigType(slaveAddress, config.getType())) {
                    log.info("Device with unitId {} and interface {} already exists in database", slaveAddress, config.getType());
                    DeviceCreateRequest d = new DeviceCreateRequest();
                    d.setUnitId(slaveAddress);
                    d.setInterfaceConfig(config);
                    dao.getAlreadyExistingDevices().add(d);
                } else {
                    log.info("Device with unitId {} and interface {} does not exist in database", slaveAddress, config.getType());
                    DeviceCreateRequest d = new DeviceCreateRequest();
                    d.setUnitId(slaveAddress);
                    d.setInterfaceConfig(config);
                    dao.getNewDevices().add(d);
                }
            }
        }
        return dao;
    }

    public static void searchModbusDevices() {
        try {
            // Set up connection parameters
            SerialParameters serialParameters = new SerialParameters();
            serialParameters.setPortName("/dev/ttyS0"); // Replace with your serial port
            serialParameters.setBaudRate(9600);
            serialParameters.setDatabits(8);
            serialParameters.setParity("None");
            serialParameters.setStopbits(1);

            // Create a serial connection
            SerialConnection connection = new SerialConnection(serialParameters);
            connection.open();

            // Loop through possible Modbus addresses and check for responses
            for (int unitId = 1; unitId <= 247; unitId++) {
                ReadInputRegistersRequest request = new ReadInputRegistersRequest(0, 1);
                request.setUnitID(unitId);

                ModbusSerialTransaction transaction = new ModbusSerialTransaction(connection);
                transaction.setRequest(request);

                try {
                    transaction.execute();
                    ReadInputRegistersResponse response = (ReadInputRegistersResponse) transaction.getResponse();

                    // If a response is received, a device is present at this address
                    System.out.println("Device found at Modbus address: " + unitId);
                } catch (Exception e) {
                    // No response received, device not present at this address
                }
            }

            // Close the connection
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
