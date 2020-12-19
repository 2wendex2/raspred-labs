package org.wendex;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;

import java.util.HashMap;

public class StorageActor extends AbstractActor {
    private HashMap<Integer, HashMap<String, Boolean>> store = new HashMap<>();

    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(TestResultMessage.class, m -> {
                    HashMap<String, Boolean> h = store.get(m.getPackageId());
                    h.put(m.getTestName(), m.getTestResult());
                }).match(TestQueryMessage.class, m -> {
                    HashMap<String, Boolean> h = store.get(m.getPackageId());
                    sender().tell(new PackageTestsMessage(h), self());
                }).build();
    }
}
