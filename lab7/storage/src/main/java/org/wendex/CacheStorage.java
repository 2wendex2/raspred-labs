package org.wendex;

import org.zeromq.SocketType;
import org.zeromq.ZMQ;

public class CacheStorage {
    private static final String PROXY_URL = "tcp://127.0.0.1:5560";

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
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket frontend = context.socket(SocketType.ROUTER);
        ZMQ.Socket backend = context.socket(SocketType.ROUTER);
        frontend.bind(FRONT_URL);
        backend.bind(BACK_URL);
        System.out.println("launch and connect broker");
        ZMQ.Poller items = context.poller (2);
        items.register(frontend, ZMQ.Poller.POLLIN);
        items.register(backend, ZMQ.Poller.POLLIN);
        boolean more = false;
        byte[] message;

        while (!Thread.currentThread().isInterrupted()) {
            items.poll(1000);
            items.pollin(FRONT_INDEX);
            if (items.pollin(FRONT_INDEX)) {
                do {
                    message = frontend.recv(0);
                    more = frontend.hasReceiveMore();
                    if (more)
                        frontend.send(message, ZMQ.SNDMORE);
                    else
                        frontend.send(BytesTools.intToBytes(4), 0);
                } while (more);
            }
            if (items.pollin(1)) {
                do {
                    message = backend.recv(0);
                    more = backend.hasReceiveMore();
                    frontend.send(message, more ? ZMQ.SNDMORE : 0);
                } while (more);
            }
        }

        items.close();
        backend.close();
        frontend.close();
        context.term();
    }
}
