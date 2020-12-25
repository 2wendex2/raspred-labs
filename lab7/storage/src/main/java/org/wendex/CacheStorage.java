package org.wendex;

public class CacheStorage {
    private int beginInterval;
    private int[] interval;

    public int getBeginInterval() {
        return beginInterval;
    }

    public int getEndInterval() {
        return beginInterval + interval.length - 1;
    }

    public int getLength() {
        return interval.length;
    }

    public int get(int index) {
        return interval[index - beginInterval];
    }

    public CacheStorage(int beginInterval, int[] interval) {
        this.beginInterval = beginInterval;
        this.interval = interval;
    }

    public static void main(String[] args) {

    }
}
