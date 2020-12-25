package org.wendex;

public class CacheProxy {
    private static final String FRONT_URL = "tcp://*:5559";
    private static final String BACK_URL = "tcp://*:5560";

    public static void main(String[] args) {
        Context context = ZMQ.context(1);
        Socket frontend =
                context.socket(SocketType.ROUTER);
        Socket backend =
                context.socket(SocketType.DEALER);
        frontend.bind("tcp://*:5559");
        backend.bind("tcp://*:5560");
        System.out.println("launch and connect
                broker.");
                Poller items = context.poller (2);
        items.register(frontend, Poller.POLLIN);
        items.register(backend, Poller.POLLIN);
        boolean more = false;
        byte[] message;
        while (!Thread.currentThread().isInterrupted()) {
            items.poll();
            if (items.pollin(0)) {
                while (true) {
                    message = frontend.recv(0);
                    more = frontend.hasReceiveMore();
                    backend.send(message, more ? ZMQ.SNDMORE : 0);
                    if(!more){
                        break;
                    }
                }
            }
            if (items.pollin(1)) {
                while (true) {
                    message = backend.recv(0);
                    more = backend.hasReceiveMore();
                    frontend.send(message, more ? ZMQ.SNDMORE : 0);
                    if(!more){
                        break;
                    }
                }
            }
        }
    }
}
