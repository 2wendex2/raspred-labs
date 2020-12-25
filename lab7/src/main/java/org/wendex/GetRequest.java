package org.wendex;

public class GetRequest extends DataRequest {
    public GetRequest(int cell) {
        this.cell = cell;
    }

    public byte[] toBytes() {
        byte[] bytes = new byte[8];
        bytes[0] = (byte)cell;
        bytes[1] = (byte)(cell >>> 8);
        bytes[2] = (byte)(cell >>> 16);
        bytes[3] = (byte)(cell >>> 24);
        return bytes;
    }

    public GetRequest(byte[] bytes) {
        cell = (bytes[0] & 0xFF) | ((bytes[1] & 0xFF) << 8) | ((bytes[2] & 0xFF) << 16) | ((bytes[3] & 0xFF) << 24);
    }
}
