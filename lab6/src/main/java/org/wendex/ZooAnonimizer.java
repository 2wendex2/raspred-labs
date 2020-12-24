package org.wendex;

import org.apache.zookeeper.*;

import java.util.List;

public class ZooAnonimizer implements Watcher {
    static final int PORT_MAX = 65535;
    static final int ZOO_PORT = 2181;

    public static void main(String[] args) throws Exception {
        if (args.length != 1)
            throw new IllegalArgumentException("Wrong arguments count");
        int port;
        try {
            port = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Port must be number", e);
        }
        if (port > PORT_MAX)
            throw new IllegalArgumentException("Port must be less then " + PORT_MAX);
        ZooAnonimizer anonimizer = new ZooAnonimizer(port);
        System.in.read();
    }

    private ZooKeeper zoo;
    private int port;

    public ZooAnonimizer(int port) throws Exception {
        this.port = port;
        this.zoo = new ZooKeeper("127.0.0.1:" + ZOO_PORT, 3000, this);
        zoo.create("/servers/s" + port, port., ZooDefs.Ids.OPEN_ACL_UNSAFE , CreateMode.EPHEMERAL);
        List<String> servers = zoo.getChildren("/servers", this);
        for (String s : servers) {
            byte[] data = zoo.getData("/servers/" + s, false, null);
            System.out.println("server " + s + " data=" + new String(data));
        }
    }

    @Override
    public void process(WatchedEvent watchedEvent) {

    }
}
