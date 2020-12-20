package org.wendex;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import akka.routing.RoundRobinPool;

public class TestRouterActor extends AbstractActor {
    static final int ROUTER_INSTANCE_NUMBER = 5;

    private ActorRef storageActor = getContext().actorOf(Props.create(StorageActor.class));
    private ActorRef testRunnerPool = getContext().actorOf(new RoundRobinPool(ROUTER_INSTANCE_NUMBER)
            .props(Props.create(TestRunnerActor.class)));

    public AbstractActor.Receive createReceive() {
        return ReceiveBuilder.create()
                .match(TestRunMessage.class, m -> {
                    testRunnerPool.tell(m, storageActor);
                }).match(TestQueryMessage.class, m -> {
                    storageActor.tell(m, sender());
                }).build();
    }
}