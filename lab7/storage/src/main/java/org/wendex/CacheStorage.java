package org.wendex;

import org.zeromq.SocketType;
import org.zeromq.ZMQ;

public class CacheStorage {
    private static final String PROXY_URL = "tcp://127.0.0.1:5560";
    private static final int NOTIFY_TIME = 10000;
    private static final int RECIEVE_TIMEOUT = 5000;
    private static final int FIRST_NOTIFY_DELTA = 1000 * NOTIFY_TIME;

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

    public void set(int index, int value) {
        interval[index - beginInterval] = value;
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
        byte[] message;
        boolean more = false;
        long curTime = System.currentTimeMillis() - FIRST_NOTIFY_DELTA;
        socket.setReceiveTimeOut(RECIEVE_TIMEOUT);
        while (!Thread.currentThread().isInterrupted()) {
            long delta = System.currentTimeMillis() - curTime;
            if (delta > NOTIFY_TIME) {
                socket.send(storage.intervalBoundsMsg());
                curTime = System.currentTimeMillis();
            }
            message = socket.recv(0);
            if (message != null) {
                do {
                    more = socket.hasReceiveMore();
                    if (more) {
                        socket.send(message, ZMQ.SNDMORE);
                        continue;
                    }
                    DataRequest request = DataRequest.fromBytes(message);
                    if (request instanceof GetRequest)
                        socket.send(BytesTools.intToBytes(storage.get(request.getCell())));
                    else {
                        PutRequest p = (PutRequest) request;
                        storage.set(p.getCell(), p.getValue());
                        socket.send(BytesTools.boolToBytes(true));
                    }
                } while (more);
            }
        }

        socket.close();
        context.term();
    }
}
