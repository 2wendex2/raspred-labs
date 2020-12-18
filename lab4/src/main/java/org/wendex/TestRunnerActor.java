package org.wendex;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;
import org.graalvm.compiler.nodes.Invokable;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.HashMap;

public class TestRunnerActor {
    static final String JS_ENGINE_NAME = "nashorn";

    public AbstractActor.Receive createReceive() {
        return ReceiveBuilder.create()
                .match(TestRunMessage.class, m -> {
                    ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName(JS_ENGINE_NAME);
                    scriptEngine.eval(m.getJsString());
                    Invokable invokable = 
                }).build();
    }
}
