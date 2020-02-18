/*
 * Copyright 2002-2016 jamod & j2mod development teams
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ghgande.j2mod.modbus.msg;

import com.ghgande.j2mod.modbus.Modbus;
import com.ghgande.j2mod.modbus.net.AbstractModbusListener;
import com.ghgande.j2mod.modbus.procimg.IllegalAddressException;
import com.ghgande.j2mod.modbus.procimg.InputRegister;
import com.ghgande.j2mod.modbus.procimg.ProcessImage;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Class implementing a <tt>ReadInputRegistersRequest</tt>. The implementation
 * directly correlates with the class 0 function <i>read multiple registers (FC
 * 4)</i>. It encapsulates the corresponding request message.
 *
 * @author Dieter Wimberger
 * @author Steve O'Hara (4NG)
 * @version 2.0 (March 2016)
 */
public class ReadWavinIndexRegistersRequest extends ModbusRequest {

    // instance attributes
    private int category;
    private int index;
    private int page;
    private int wordCount;


    /**
     * Constructs a new <tt>ReadInputRegistersRequest</tt> instance.
     */
    public ReadWavinIndexRegistersRequest() {
        super();

        setFunctionCode(0x43);
        // 4 bytes (unit id and function code is excluded)
        setDataLength(4);
    }

    /**
     * Constructs a new <tt>ReadInputRegistersRequest</tt> instance with a given
     * reference and count of words to be read.
     * <p>
     *
     * @param category, index, page, the register to read from.
     * @param count     the number of words to be read.
     */
    public ReadWavinIndexRegistersRequest(int category, int index, int page, int count) {
        super();

        setFunctionCode(0x43);
        // 4 bytes (unit id and function code is excluded)
        setDataLength(4);
        setCategory(category);
        setIndex(index);
        setPage(page);
        setWordCount(count);
    }

    @Override
    public ReadInputRegistersResponse getResponse() {
        ReadInputRegistersResponse response = (ReadInputRegistersResponse) updateResponseWithHeader(new ReadInputRegistersResponse());
        response.setWordCount(getWordCount());
        return response;
    }

    @Override
    public ModbusResponse createResponse(AbstractModbusListener listener) {
        return createExceptionResponse(Modbus.ILLEGAL_FUNCTION_EXCEPTION);
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    /**
     * Returns the number of words to be read with this
     * <tt>ReadInputRegistersRequest</tt>.
     * <p>
     *
     * @return the number of words to be read as <tt>int</tt>.
     */
    public int getWordCount() {
        return wordCount;
    }

    /**
     * Sets the number of words to be read with this
     * <tt>ReadInputRegistersRequest</tt>.
     * <p>
     *
     * @param count the number of words to be read.
     */
    public void setWordCount(int count) {
        wordCount = count;
    }

    @Override
    public void writeData(DataOutput dout) throws IOException {
        dout.writeByte(category);
        dout.writeByte(index);
        dout.writeByte(page);
        dout.writeByte(wordCount);
    }

    @Override
    public void readData(DataInput din) throws IOException {
        wordCount = din.readUnsignedByte();
    }

    @Override
    public byte[] getMessage() {
        byte[] result = new byte[4];
        result[0] = (byte) (category & 0xff);
        result[1] = (byte) (index & 0xff);
        result[2] = (byte) (page & 0xff);
        result[3] = (byte) (wordCount & 0xff);
        return result;
    }
}