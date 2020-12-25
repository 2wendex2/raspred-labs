package org.wendex;

public class Storage {
    private byte[] routerFrame;
    private int beginInterval;
    private int endInterval;

    public int getBeginInterval() {
        return beginInterval;
    }

    public byte[] getRouterFrame() {
        return routerFrame;
    }

    public int getEndInterval() {
        return endInterval;
    }

    public Storage(byte[] routerFrame, int beginInterval, int endInterval) {
        this.routerFrame = routerFrame;
        this.beginInterval = beginInterval;
        this.endInterval = endInterval;
    }
}
