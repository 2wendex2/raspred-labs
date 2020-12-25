package org.wendex;

import org.zeromq.SocketType;
import org.zeromq.ZMQ;

public class CacheStorage {
    private static final String PROXY_URL = "tcp://127.0.0.1:5560";
    private static final int NOTIFY_TIME = 10000;
    private static final int RECIEVE_TIMEOUT = 5000;

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

    private byte[] intervalBoundsMsg() {
        byte[] bytes = new byte[8];
        BytesTools.intToBytesOff(beginInterval, bytes, 0);
        BytesTools.intToBytesOff(getEndInterval(), bytes, 4);
        return bytes;
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
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket socket = context.socket(SocketType.DEALER);
        socket.connect(PROXY_URL);
        System.out.println("launch and connect storage");
        boolean more = false;
        byte[] message;
        long curTime = System.currentTimeMillis();
        socket.setReceiveTimeOut(RECIEVE_TIMEOUT);
        while (!Thread.currentThread().isInterrupted()) {
            long delta = System.currentTimeMillis() - curTime;
            if (delta > NOTIFY_TIME) {
                socket.send(storage.intervalBoundsMsg());
                curTime = System.currentTimeMillis();
            }
                do {
                    message = frontend.recv(0);
                    more = frontend.hasReceiveMore();
                    if (more)
                        frontend.send(message, ZMQ.SNDMORE);
                    else
                        frontend.send(BytesTools.intToBytes(4), 0);
                } while (more);
        }

        items.close();
        backend.close();
        frontend.close();
        context.term();
    }
}
