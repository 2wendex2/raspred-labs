package org.wendex;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;

import java.util.Random;

public class ZooActor extends AbstractActor {
    private int[] ports = new int[0];
    private Random random = new Random();

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(ServerListMessage.class, m -> ports = m.getPorts())
                .match(ServerQueryMessage.class, m -> {
                    int index = random.nextInt(ports.length);

                })
    }
}
