package org.wendex;

import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.model.Query;
import akka.japi.Pair;
import akka.pattern.Patterns;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;

import java.io.IOException;
import java.util.concurrent.CompletionStage;

public class LoadTestApp {
    static final int MAP_ASYNC_PARALLELISM = 1;

    private static Flow<HttpRequest, HttpResponse, NotUsed> getRouteFlow(Http http, ActorSystem actorSystem,
                                                                         ActorMaterializer actorMaterializer) {
        ActorRef actor = actorSystem.actorOf(Props.create(StoreActor.class));
        Flow.of(HttpRequest.class).map(x -> {
            Query q = x.getUri().query();
            return new Pair<String, Integer>(q.get("testUrl").get(), Integer.parseInt(q.get("count").get()));
        }).mapAsync(MAP_ASYNC_PARALLELISM, x -> {
            Patterns.ask(actor, new QueryMessage(x), )
        });
    }

    public static void main(String[] args) throws IOException {
        System.out.println("start!");
        ActorSystem system = ActorSystem.create("routes");
        final Http http = Http.get(system);
        final ActorMaterializer materializer = ActorMaterializer.create(system);
        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow =

        final CompletionStage<ServerBinding> binding = http.bindAndHandle(
                routeFlow,
                ConnectHttp.toHost("localhost", 8080),
                materializer
        );
        System.out.println("Server online at http://localhost:8080/\nPress RETURN to stop...");
        System.in.read();
        binding
                .thenCompose(ServerBinding::unbind)
                .thenAccept(unbound -> system.terminate());
    }
}