package org.wendex;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.server.Route;
import akka.pattern.Patterns;
import akka.stream.ActorMaterializer;
import akka.http.javadsl.Http;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.NotUsed;
import akka.stream.javadsl.Flow;

import java.io.IOException;
import java.util.concurrent.CompletionStage;

import static akka.http.javadsl.server.Directives.*;
import static akka.http.javadsl.server.Directives.completeOKWithFuture;


public class JSTest {
    private static final String PROPERTY_PACKAGE_ID = "packageId";
    private static final int QUERY_TIMEOUT = 10000;

    public static Route createRoute(ActorRef actor) {
        return post(() -> entity(Jackson.unmarshaller(HttpQuery.class), m -> {
            System.out.println(m.getFunctionName());
            System.out.println(m.getJsScript());
            System.out.println(m.getPackageId());
            System.out.println(m.getTests()[0].getExpectedResult());
            for (Test t : m.getTests()) {
                actor.tell(new TestRunMessage(m.getPackageId(), m.getFunctionName(), m.getJsScript(),
                        t.getParams(), t.getTestName(), t.getExpectedResult()), ActorRef.noSender());
            }
            return complete("SUCCESS");
        })).orElse(get(() -> parameter(PROPERTY_PACKAGE_ID, m -> {
            return completeOKWithFuture(Patterns.ask(actor, new TestQueryMessage(Integer.parseInt(m)), QUERY_TIMEOUT),
                    Jackson.marshaller());
        })));
    }

    public static void main(String[] args) throws IOException {
        ActorSystem actorSystem = ActorSystem.create("test");
        ActorRef testRouterActor = actorSystem.actorOf(Props.create(TestRouterActor.class));
        final Http http = Http.get(actorSystem);
        final ActorMaterializer materializer = ActorMaterializer.create(actorSystem);
        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow =
                createRoute(testRouterActor).flow(actorSystem, materializer);
        final CompletionStage<ServerBinding> binding = http.bindAndHandle(
                routeFlow, ConnectHttp.toHost("localhost", 8080), materializer);
        System.in.read();
        binding.thenCompose(ServerBinding::unbind)
               .thenAccept(unbound -> actorSystem.terminate());
    }
}