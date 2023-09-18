package com.mamotec.energycontrolbackend.client;

import com.ghgande.j2mod.modbus.io.ModbusTCPTransaction;
import com.ghgande.j2mod.modbus.msg.ReadInputRegistersRequest;
import com.ghgande.j2mod.modbus.msg.ReadInputRegistersResponse;
import com.ghgande.j2mod.modbus.msg.ReadMultipleRegistersRequest;
import com.ghgande.j2mod.modbus.msg.ReadMultipleRegistersResponse;
import com.ghgande.j2mod.modbus.net.TCPMasterConnection;
import com.ghgande.j2mod.modbus.procimg.Register;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;

@Slf4j
public class ModbusTCPClient {

    private TCPMasterConnection connection;

    public ModbusTCPClient(String host, int port) {
        try {
            InetAddress serverAddress = InetAddress.getByName(host);
            connection = new TCPMasterConnection(serverAddress);
            connection.setPort(port);
            connection.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void connect() throws Exception {
        if (!connection.isConnected()) {
            connection.connect();
        }
    }

    public void disconnect() {
        if (connection.isConnected()) {
            connection.close();
        }
    }

    public ReadInputRegistersResponse readInputRegisters(int startAddress, int quantity, int unitID) throws Exception {
        // Erstellen Sie eine Modbus-Transaktion
        ModbusTCPTransaction transaction = new ModbusTCPTransaction(connection);

        // Erstellen Sie eine Anfrage, um Input-Register auszulesen
        ReadInputRegistersRequest request = new ReadInputRegistersRequest(startAddress, quantity);
        request.setUnitID(unitID);

        // Setzen Sie die Anfrage in die Transaktion
        transaction.setRequest(request);

        // Führen Sie die Transaktion aus
        transaction.execute();

        // Erhalten Sie die Antwort
        ReadInputRegistersResponse response = (ReadInputRegistersResponse) transaction.getResponse();

        if (response != null) {
            log.info(response.toString());
            return response;
        } else {
            throw new Exception("Keine Antwort erhalten.");
        }
    }

    public ReadMultipleRegistersResponse readHoldingRegisters(int startAddress, int quantity, int unitID) throws Exception {
        ModbusTCPTransaction transaction = new ModbusTCPTransaction(connection);

        ReadMultipleRegistersRequest request = new ReadMultipleRegistersRequest(startAddress, quantity);
        request.setUnitID(unitID);

        transaction.setRequest(request);

        transaction.execute();

        ReadMultipleRegistersResponse response = (ReadMultipleRegistersResponse) transaction.getResponse();

        if (response != null) {
            Register[] registerValues = response.getRegisters();
            for (int i = 0; i < registerValues.length; i++) {
                log.info("Register " + (i) + ": " + registerValues[i]);
            }
        }

        connection.close();

        return null;

    }
}
