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
    private ActorRef storageActor = getContext().actorOf(Props.create(StorageActor.class));
    private RoundRobinPool testRunnerPool = getContext().actorOf(new RoundRobinPool(5)
            .props(Props.create(TestRouterActor.class)))

    public TestRouterActor() {

    }

    public AbstractActor.Receive createReceive() {
        return ReceiveBuilder.create()
                .match(TestRunMessage.class, m -> {
                    /*ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("p");
                    scriptEngine.eval(m.getJsString());
                    Invocable invocable = (Invocable)scriptEngine;
                    System.out.println(invocable.invokeFunction(m.getFunctionName(), m.getParams()));*/
                }).build();
    }
}