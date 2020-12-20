package org.wendex;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import akka.routing.RoundRobinPool;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class TestRouterActor extends AbstractActor {
    static final int ROUTER_INSTANCE_NUMBER = 1;

    private ActorRef storageActor = getContext().actorOf(Props.create(StorageActor.class));
    private ActorRef testRunnerPool = getContext().actorOf(new RoundRobinPool(ROUTER_INSTANCE_NUMBER)
            .props(Props.create(TestRunnerActor.class)));

    public AbstractActor.Receive createReceive() {
        return ReceiveBuilder.create()
                .match(TestRunMessage.class, m -> {
                    System.out.println("pun");
                    testRunnerPool.tell(m, storageActor);
                }).match(TestQueryMessage.class, m -> {
                    storageActor.tell(m, sender());
                }).build();
    }
}