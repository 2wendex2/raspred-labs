package org.wendex;

public class Storage {
    private long notificationTime = 0;
    private int beginInterval;
    private int endInterval;

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
