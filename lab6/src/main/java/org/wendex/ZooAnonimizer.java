package org.wendex;

import org.apache.zookeeper.ZooKeeper;

public class ZooAnonimizer {


    public static void main(String[] args) {
        ZooKeeper zoo = new ZooKeeper("127.0.0.1:2181", 3000, this);
        zoo.create("/servers/s",
                “data”.getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE ,
                CreateMode.EPHEMERAL_SEQUENTIAL
);
        List<String> servers = zoo.getChildren("/servers", this);
        for (String s : servers) {
            byte[] data = zoo.getData("/servers/" + s, false, null);
            System.out.println("server " + s + " data=" + new String(data));
        }
    }
}
