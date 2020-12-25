package org.wendex;

public class BytesTools {
    static public byte[] intToBytes(int v) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte)v;
        bytes[1] = (byte)(v >>> 8);
        bytes[2] = (byte)(v >>> 16);
        bytes[3] = (byte)(v >>> 24);
        return bytes;
    }

    static public int bytesToInt(byte[] bytes){
        return (bytes[0] & 0xFF) | ((bytes[1] & 0xFF) << 8) | ((bytes[2] & 0xFF) << 16) | ((bytes[3] & 0xFF) << 24);
    }

    static public void intToBytesOff(int v, byte[] bytes, int off) {
        bytes[off] = (byte)v;
        bytes[off + 1] = (byte)(v >>> 8);
        bytes[off + 2] = (byte)(v >>> 16);
        bytes[off + 3] = (byte)(v >>> 24);
    }

    static public int bytesToIntOff(byte[] bytes, int off){
        return (bytes[off] & 0xFF) | ((bytes[off + 1] & 0xFF) << 8) | ((bytes[off + 2] & 0xFF) << 16)
                | ((bytes[off + 3] & 0xFF) << 24);
    }

    static public byte[] boolToBytes(boolean b) {
        byte[] bytes = new byte[1];
        if (b)
            bytes[0] = 1;
        else
            bytes[0] = 0;
        return bytes;
    }

    static public boolean bytesToBool(byte[] bytes) {
        return bytes[0] != 0;
    }
}
