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

    public void setEndInterval(int endInterval) {
        this.endInterval = endInterval;
    }

    public void setBeginInterval(int beginInterval) {
        this.beginInterval = beginInterval;
    }



    public Storage(byte[] routerFrame, int beginInterval, int endInterval) {
        this.routerFrame = routerFrame;
        this.beginInterval = beginInterval;
        this.endInterval = endInterval;
    }
}
