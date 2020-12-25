package org.wendex;

public class CacheStorage {
    private int intervalBegin;
    private int[] interval;

    public int getIntervalBegin() {
        return intervalBegin;
    }

    public int getLength() {
        return interval.length;
    }

    public int get(int index) {
        return interval[index - intervalBegin];
    }
}
