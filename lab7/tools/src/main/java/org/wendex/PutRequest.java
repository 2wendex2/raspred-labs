package org.wendex;

public class PutRequest extends DataRequest {
    private int value;

    public PutRequest(int cell, int value) {
        this.cell = cell;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public byte[] toBytes() {
        byte[] bytes = new byte[8];
        BytesTools.intToBytesOff(cell, bytes,0);
        BytesTools.intToBytesOff(value, bytes,4);
        return bytes;
    }

    public PutRequest(byte[] bytes) {
        cell = BytesTools.bytesToIntOff(bytes, 0);
        value = BytesTools.bytesToIntOff(bytes, 4);
    }
}
