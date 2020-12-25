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
}
