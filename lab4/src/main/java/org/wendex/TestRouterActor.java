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
    //private ActorRef storageActor = getContext().actorOf(Props.create(StorageActor.class));
    private ActorRef testRunnerPool = getContext().actorOf(new RoundRobinPool(1)
            .props(Props.create(TestRouterActor.class)));

    public AbstractActor.Receive createReceive() {
        return ReceiveBuilder.create()
                .match(TestRunMessage.class, m -> {
                    //testRunnerPool.tell(m, self());
                }).match(TestQueryMessage.class, m -> {
                    //storageActor.tell(m, self());
                }).match(PackageTestsMessage.class, m -> {
                    //storageActor.tell(m, self());
                }).build();
    }
}