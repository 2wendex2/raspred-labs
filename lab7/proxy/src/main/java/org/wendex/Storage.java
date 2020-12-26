package org.wendex;

public class Storage {
    private long notificationTime = System.currentTimeMillis();
    private int beginInterval;
    private int endInterval;
    private byte[] route

    public int getBeginInterval() {
        return beginInterval;
    }

    public long getNotificationTime() {
        return notificationTime;
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

    public void setNotificationTime(long notificationTime) {
        this.notificationTime = notificationTime;
    }

    public Storage(int beginInterval, int endInterval) {
        this.beginInterval = beginInterval;
        this.endInterval = endInterval;
    }
}
