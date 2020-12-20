package org.wendex;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;

import javax.script.ScriptEngine;
import javax.script.Invocable;
import javax.script.ScriptEngineManager;

public class TestRunnerActor extends  AbstractActor{
    static final String JS_ENGINE_NAME = "nashorn";

    public AbstractActor.Receive createReceive() {
        return ReceiveBuilder.create()
                .match(TestRunMessage.class, m -> {
                    System.out.println("pun");
                    ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName(JS_ENGINE_NAME);
                    scriptEngine.eval(m.getJsString());
                    Invocable invocable = (Invocable)scriptEngine;
                    Object o = invocable.invokeFunction(m.getFunctionName(), m.getParams());
                    sender().tell(m.toTestResultMessage(o), self());
                }).build();
    }
}
