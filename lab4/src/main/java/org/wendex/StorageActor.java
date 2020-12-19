package org.wendex;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;

import java.util.HashMap;

public class StorageActor extends AbstractActor {
    private HashMap<Integer, HashMap<String, Boolean>> store;

    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(TestResultMessage.class, m -> {
                    HashMap<String, Boolean> h = store.get(m.getPackageId());
                    h.put(m.getTestName(), m.getTestResult());
                }).match(TestQueryMessage.class, m -> {
                    sender().tell(new PackageTestsMessage(store.get(m.getPackageId())), self());
                }).build();
    }
}
