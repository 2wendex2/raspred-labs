package org.wendex;

public class GetRequest extends DataRequest {
    public GetRequest(int cell) {
        this.cell = cell;
    }

    public byte[] toBytes() {
        return BytesTools.intToBytes(cell);
    }

    public GetRequest(byte[] bytes) {
        cell = BytesTools.bytesToInt(bytes);
    }
}
