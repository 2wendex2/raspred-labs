import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import org.wendex.TestRouterActor;

import javax.script.ScriptEngine;

public class JSTest {
    public static void main(String[] args) {
        ActorSystem actorSystem = ActorSystem.create("test");
        ActorRef storageActor = actorSystem.actorOf(Props.create(TestRouterActor.class));
        
    }
}