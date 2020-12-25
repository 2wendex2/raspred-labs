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

    static CacheStorage fromStrings(String[] args) {
        int beginInterval = Integer.parseInt(args[0]);
        int endInterval = Integer.parseInt(args[1]);
        int length = endInterval - beginInterval + 1;
        int[] interval = new int[length];
        for (int i = 0; i < length; i++)
            interval[i] = Integer.parseInt(args[i + 2]);
        return new CacheStorage(beginInterval, interval);
    }

    public static void main(String[] args) {
        CacheStorage storage = fromStrings(args);

    }
}
