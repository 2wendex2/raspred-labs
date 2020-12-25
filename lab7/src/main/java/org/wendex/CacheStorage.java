package org.wendex;

public class CacheStorage {
    private int intervalBegin;
    private int intervalEnd;

    public int getIntervalBegin() {
        return intervalBegin;
    }

    public int getIntervalEnd() {
        return intervalEnd;
    }

    public int getLength() {
        return intervalEnd - intervalEnd + 1;
    }
}
