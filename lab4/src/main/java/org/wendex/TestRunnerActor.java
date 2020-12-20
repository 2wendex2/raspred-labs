package org.wendex;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;

import javax.script.ScriptEngine;
import javax.script.Invocable;
import javax.script.ScriptEngineManager;

public class TestRunnerActor extends  AbstractActor{
    private static final String JS_ENGINE_NAME = "nashorn";
    private static final String EQUALLER_FUNCTION_NAME = "eq";
    private static Invocable equaller;

    static {
        ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName(JS_ENGINE_NAME);
        try {
            scriptEngine.eval("var " + EQUALLER_FUNCTION_NAME + " = function(a, b) {a == b}");
        } catch (Exception ex) {
            throw new RuntimeException("equal function eval error", ex);
        }
        equaller = (Invocable)scriptEngine;
    }

    public AbstractActor.Receive createReceive() {
        return ReceiveBuilder.create()
                .match(TestRunMessage.class, m -> {
                    ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName(JS_ENGINE_NAME);
                    scriptEngine.eval(m.getJsString());
                    Invocable invocable = (Invocable)scriptEngine;
                    Object o = invocable.invokeFunction(m.getFunctionName(), m.getParams());
                    Object b = equaller.invokeFunction(EQUALLER_FUNCTION_NAME, o, m.getTestResult());
                    sender().tell(new TestResultMessage(m.getPackageId(), m.getTestName(), (Boolean)b), self());
                }).build();
    }
}
