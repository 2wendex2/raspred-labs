package org.wendex;

public class PutRequest extends DataRequest {
    int value;

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
        cell = (bytes[0] & 0xFF) | ((bytes[1] & 0xFF) << 8) | ((bytes[2] & 0xFF) << 16) | ((bytes[3] & 0xFF) << 24);
        value = (bytes[4] & 0xFF) | ((bytes[5] & 0xFF) << 8) | ((bytes[6] & 0xFF) << 16) | ((bytes[7] & 0xFF) << 24);
    }
}
