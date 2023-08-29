package com.mamotec.energycontrolbackend.cron;

import me.retrodaredevil.io.modbus.ModbusMessage;

public class Opfeer implements ModbusMessage {
    @Override
    public int getFunctionCode() {
        return 3;
    }

    @Override
    public byte getByteFunctionCode() {
        return 0;
    }

    @Override
    public int[] getData() {
        return new int[0];
    }

    @Override
    public byte[] getByteData() {
        return new byte[0];
    }
}
