package org.wendex;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;

import javax.script.ScriptEngine;
import java.util.HashMap;

public class TestRunnerActor {
    public AbstractActor.Receive createReceive() {
        return ReceiveBuilder.create()
                .match(TestRunMessage.class, m -> {
                    ScriptEngine scriptEngine 
                }).build();
    }
}
