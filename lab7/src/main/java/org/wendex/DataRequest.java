package org.wendex;

public abstract class DataRequest {
    protected int cell;

    public int getCell() {
        return cell;
    }

    public static DataRequest getFromBytes(byte[] bytes) {

    }
}
