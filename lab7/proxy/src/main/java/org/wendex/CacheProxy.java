package org.wendex;

import org.zeromq.SocketType;
import org.zeromq.ZMQ;

public class CacheProxy {
    private static final String FRONT_URL = "tcp://*:5559";
    private static final String BACK_URL = "tcp://*:5560";

    private static int FRONT_INDEX = 0;
    private static int BACK_INDEX = 1;

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

        message = frontend.recv(0);
        System.out.println(message.length);
        System.out.println(message[0]);
        System.out.println(message[1]);
        System.out.println(message[2]);
        System.out.println(message[3]);
        System.out.println(message[4]);
        frontend.send(message, ZMQ.SNDMORE);

        message = frontend.recv(0);
        System.out.println(message.length);
        System.out.println(message[0]);
        System.out.println(message[1]);
        System.out.println(message[2]);
        System.out.println(message[3]);

        /*while (!Thread.currentThread().isInterrupted()) {
            items.poll(1000);
            items.pollin(FRONT_INDEX);
            frontend.recv(0);
            /*if (items.pollin(FRONT_INDEX)) {
                do {
                    throw new IllegalStateException();
                   // message = frontend.recv(0);

                    //more = frontend.hasReceiveMore();
                    //backend.send(message, more ? ZMQ.SNDMORE : 0);
                    /*DataRequest request = DataRequest.fromBytes(frontend.recv(0));
                    more = frontend.hasReceiveMore();
                    if (request instanceof GetRequest)
                        frontend.send(BytesTools.intToBytes(4), more ? ZMQ.SNDMORE : 0);
                    else
                        frontend.send(BytesTools.boolToBytes(true), more ? ZMQ.SNDMORE : 0);
                } while (more);
            }
            if (items.pollin(1)) {
                do {
                    message = backend.recv(0);
                    more = backend.hasReceiveMore();
                    frontend.send(message, more ? ZMQ.SNDMORE : 0);
                } while (more);
            }
        }*/

        items.close();
        backend.close();
        frontend.close();
        context.term();
    }
}
