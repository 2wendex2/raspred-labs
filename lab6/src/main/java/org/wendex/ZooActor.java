package org.wendex;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;

public class ZooActor extends AbstractActor {
    private int[] ports;

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(ServerListMessage.class,)
    }
}
