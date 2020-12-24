package org.wendex;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;

import java.util.Random;

public class ZooActor extends AbstractActor {
    private int[] ports;
    private Random random = new Random();

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(ServerListMessage.class, m -> {
                        ports = m.getPorts();
                        for (int i : m.getPorts())
                            System.out.println(i);
                })
                .match(ServerQueryMessage.class, m -> {
                    int index = random.nextInt(ports.length);
                    sender().tell(new ServerUrlMessage("http://127.0.0.1:" + ports[index]), self());
                }).build();
    }

    public ZooActor(int[] ports) {
        this.ports = ports;
    }
}
