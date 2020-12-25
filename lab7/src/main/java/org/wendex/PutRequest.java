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
        bytes[0] = (byte)cell;
        bytes[1] = (byte)(cell >>> 8);
        bytes[2] = (byte)(cell >>> 16);
        bytes[3] = (byte)(cell >>> 24);

        bytes[4] = (byte)value;
        bytes[5] = (byte)(value >>> 8);
        bytes[6] = (byte)(value >>> 16);
        bytes[7] = (byte)(value >>> 24);
        return bytes;
    }

    public PutRequest(byte[] bytes) {
        cell = (bytes[0] & 0xFF) | ((bytes[1] & 0xFF) << 8) | ((bytes[2] & 0xFF) << 16) | ((bytes[3] & 0xFF) << 24);
        value = (bytes[4] & 0xFF) | ((bytes[5] & 0xFF) << 8) | ((bytes[6] & 0xFF) << 16) | ((bytes[7] & 0xFF) << 24);
    }
}
