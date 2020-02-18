package com.ghgande.j2mod.modbus.facade;

import com.ghgande.j2mod.modbus.ModbusException;
import com.ghgande.j2mod.modbus.msg.ReadInputRegistersResponse;
import com.ghgande.j2mod.modbus.msg.ReadWavinIndexRegistersRequest;
import com.ghgande.j2mod.modbus.msg.ReadWavinRegistersResponse;
import com.ghgande.j2mod.modbus.procimg.InputRegister;
import com.ghgande.j2mod.modbus.util.SerialParameters;

public class ModbusWavinMaster extends ModbusSerialMaster {

private ReadWavinIndexRegistersRequest readWavinIndexRegistersRequest;

    public synchronized InputRegister[] readIndexRegisters(int unitId, int category, int index, int page, int count) throws ModbusException {
        checkTransaction();
        if (readWavinIndexRegistersRequest == null) {
            readWavinIndexRegistersRequest = new ReadWavinIndexRegistersRequest();
        }
        readWavinIndexRegistersRequest.setUnitID(unitId);
        readWavinIndexRegistersRequest.setCategory(category);
        readWavinIndexRegistersRequest.setIndex(index);
        readWavinIndexRegistersRequest.setPage(page);

        readWavinIndexRegistersRequest.setWordCount(count);
        transaction.setRequest(readWavinIndexRegistersRequest);
        transaction.execute();
        return ((ReadWavinRegistersResponse) getAndCheckResponse()).getRegisters();
    }

    public ModbusWavinMaster(SerialParameters param) {
        super(param);
    }

    public ModbusWavinMaster(SerialParameters param, int timeout) {
        super(param, timeout);
    }

    public ModbusWavinMaster(SerialParameters param, int timeout, int transDelay) {
        super(param, timeout, transDelay);
    }
}