package org.wendex;

import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import org.wendex.TestRouterActor;
import org.wendex.TestRunMessage;

import javax.script.ScriptEngine;

public class JSTest {
    public static void main(String[] args) {
        ActorSystem actorSystem = ActorSystem.create("test");
        ActorRef storageActor = actorSystem.actorOf(Props.create(TestRouterActor.class));
        storageActor.tell(new TestRunMessage(1, "divideFn",
                "var divideFn = function(a,b) { return a/b}", "[4,2]"), ActorRef.noSender());
    }
}