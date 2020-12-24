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
    private String path;

    private static byte[] portToBytes(int port) {
        byte[] bytes = new byte[2];
        bytes[0] = (byte)port;
        bytes[1] = (byte)(port >>> 8);
        return bytes;
    }

    private static int bytesToPort(byte[] bytes) {
        return bytes[0] | (bytes[1] << 8);
    }

    public ZooAnonimizer(int port) throws Exception {
        this.port = port;
        this.zoo = new ZooKeeper("127.0.0.1:" + ZOO_PORT, 3000, this);
        this.path = "/servers/s" + port;
        zoo.create(path, portToBytes(port), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
    }

    private int[] getPortsList() throws Exception {
        List<String> servers = zoo.getChildren("/servers", this);
        int[] prts = new int[servers.size()];
        int i = 0;
        for (String s : servers) {
            prts[i] = bytesToPort(zoo.getData("/servers/" + s, false, null));
            i++;
        }
        return prts;
    }

    @Override
    public void process(WatchedEvent watchedEvent) {

    }
}
