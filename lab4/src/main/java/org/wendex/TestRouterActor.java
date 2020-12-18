package org.wendex;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.japi.pf.ReceiveBuilder;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class TestRouterActor  {
    private ActorRef storageActor = 

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