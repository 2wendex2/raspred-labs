package org.wendex;

import org.zeromq.SocketType;
import org.zeromq.ZMQ;
import org.zeromq.ZMQQueue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CacheProxy {
    private static final String FRONT_URL = "tcp://*:5559";
    private static final String BACK_URL = "tcp://*:5560";
    private static final byte[] EMPTY_MESSAGE = new byte[0];

    private static final int FRONT_INDEX = 0;
    private static final int BACK_INDEX = 1;
    private static final int NOTIFY_MSG_LENTH = 8;
    private static final int GETEND_MSG_LENTH = 4;
    private static final int PUTEND_MSG_LENGTH = 1;
    private static final long STORAGE_TIMEOUT = 60000;

    public static byte[] randomStorage(HashMap<byte[], Storage> storages, int index) {
        long curTime = System.currentTimeMillis();
        for (Map.Entry<byte[], Storage> entry : storages.entrySet()) {
            byte[] k = entry.getKey();
            Storage v = entry.getValue();
            if (v.)
        }
    }

    public static void main(String[] args) {
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
        byte[] storage;
        byte[] client;
        HashMap<byte[], Storage> storages = new HashMap<>();


        while (!Thread.currentThread().isInterrupted()) {
            items.poll(1000);
            items.pollin(FRONT_INDEX);
            if (items.pollin(FRONT_INDEX)) {
                do {
                    message = frontend.recv(0);
                    more = frontend.hasReceiveMore();
                    if (more)
                        backend.send(message, ZMQ.SNDMORE);
                    else
                        backend.send(message, 0);
                } while (more);
            }
            if (items.pollin(BACK_INDEX)) {
                storage = backend.recv(0);
                backend.recv(0);
                message = backend.recv();
                if (!backend.hasReceiveMore()) {
                    int beginInterval = BytesTools.bytesToIntOff(message, 0);
                    int endInterval = BytesTools.bytesToIntOff(message, 4);
                    Storage s = storages.get(storage);
                    s.setBeginInterval(beginInterval);
                    s.setEndInterval(endInterval);
                    s.setNotificationTime(0);
                }
                else {
                    frontend.send(message, ZMQ.SNDMORE);
                    frontend.send(backend.recv(), ZMQ.SNDMORE);
                    frontend.send(backend.recv(), 0);
                }
            }
        }

        items.close();
        backend.close();
        frontend.close();
        context.term();
    }
}
