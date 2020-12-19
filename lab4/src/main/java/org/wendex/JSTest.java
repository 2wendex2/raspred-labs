package org.wendex;

import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import org.wendex.TestRouterActor;
import org.wendex.TestRunMessage;
import akka.http.javadsl.Htt;

import javax.script.ScriptEngine;

public class JSTest {
    public static void main(String[] args) {
        ActorSystem actorSystem = ActorSystem.create("test");
        final Http http = Http.get(actorSystem);
        final ActorMaterializer materializer = ActorMaterializer.create(system);
        MainHttp instance = new MainHttp(system);
        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow =
                instance.createRoute(system).flow(system, materializer);
        final CompletionStage<ServerBinding> binding = http.bindAndHandle(
                routeFlow,
                ConnectHttp.toHost("localhost", 8080),
                materializer
        );
        ActorRef testRouterActor = actorSystem.actorOf(Props.create(TestRouterActor.class));
        testRouterActor
    }
}