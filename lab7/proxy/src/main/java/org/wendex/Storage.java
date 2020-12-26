package org.wendex;

public class Storage {
    private long notificationTime = System.currentTimeMillis();
    private int beginInterval;
    private int endInterval;
    private byte[] routeFrame;

    public int getBeginInterval() {
        return beginInterval;
    }

    public long getNotificationTime() {
        return notificationTime;
    }

    public byte[] getRouteFrame() {
        return routeFrame;
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

    public void updateNotificationTime() {
        notificationTime = System.currentTimeMillis();
    }

    public Storage(int beginInterval, int endInterval, byte[] routeFrame) {
        this.beginInterval = beginInterval;
        this.endInterval = endInterval;
        this.routeFrame = routeFrame;
    }
}
