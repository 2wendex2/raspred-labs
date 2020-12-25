package org.wendex;

public abstract class DataRequest {
    protected int cell;

    public int getCell() {
        return cell;
    }

    public static DataRequest fromBytes(byte[] bytes) {
        if (bytes.length == 4)
            return new GetRequest(bytes);
        else
            return new PutRequest(bytes);
    }
}
