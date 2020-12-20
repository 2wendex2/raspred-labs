package org.wendex;

import akka.actor.AbstractActor;
import akka.japi.Pair;
import akka.japi.pf.ReceiveBuilder;

import java.util.HashMap;

public class StoreActor extends AbstractActor {
    private HashMap<Pair<String, Integer>, Long> store = new HashMap<>();

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(ResultMessage.class, m -> {
                    store.put(m.getPair(), m.getTime());
                }).match(QueryMessage.class, m -> {
                    
                })
                .build();
    }
}
