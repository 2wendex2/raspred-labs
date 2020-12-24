package org.wendex;

import org.apache.zookeeper.*;

import java.util.List;

public class ZooAnonimizer extends Watcher{


    public static void main(String[] args) throws Exception {

    }

    public ZooAnonimizer() {
        ZooKeeper zoo = new ZooKeeper("127.0.0.1:2181", 3000, watcher);
        zoo.create("/servers/s", "data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE , CreateMode.EPHEMERAL_SEQUENTIAL);
        List<String> servers = zoo.getChildren("/servers", watcher);
        for (String s : servers) {
            byte[] data = zoo.getData("/servers/" + s, false, null);
            System.out.println("server " + s + " data=" + new String(data));
        }
    }

    p
}
