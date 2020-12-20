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
import akka.stream.javadsl.Keep;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import org.asynchttpclient.AsyncHttpClient;

import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static akka.http.javadsl.server.Directives.completeOKWithFuture;
import static org.asynchttpclient.Dsl.asyncHttpClient;

public class LoadTestApp {
    static final int MAP_ASYNC_PARALLELISM = 1;
    private static final int QUERY_TIMEOUT = 10000;
    private static final String PROPERTY_TEST_URL = "testUrl";
    private static final String PROPERTY_COUNT = "count";
    private static final String HOST_NAME = "localhost";
    private static final int PORT = 8080;

    private static Flow<HttpRequest, HttpResponse, NotUsed> getRouteFlow(Http http, ActorSystem actorSystem,
                                ActorMaterializer actorMaterializer, AsyncHttpClient client) {
        ActorRef actor = actorSystem.actorOf(Props.create(StoreActor.class));
        Flow.of(HttpRequest.class).map(x -> {
            Query q = x.getUri().query();
            return new Pair<String, Integer>(q.get(PROPERTY_TEST_URL).get(),
                    Integer.parseInt(q.get(PROPERTY_COUNT).get()));
        }).mapAsync(MAP_ASYNC_PARALLELISM, x -> {
            Patterns.ask(actor, new QueryMessage(x), Duration.ofMillis(QUERY_TIMEOUT))
                    .thenCompose(s -> {
                        ResultMessage r = (ResultMessage)s;
                        if (r.getTime() != null)
                            return CompletableFuture.completedFuture(r.getTime());

                        Sink<Long, CompletionStage<Long>> fold = Sink.fold(0, (a, b) -> a.add)

                        Sink<QueryMessage, CompletionStage<Long>> testSink = Flow
                                .<QueryMessage>create()
                                .mapConcat(t -> Collections.nCopies(t.getCount(), t.getTestUrl()))
                                .mapAsync(MAP_ASYNC_PARALLELISM, t -> {
                                    long startTime = System.currentTimeMillis();
                                    return client.prepareGet(t)
                                            .execute()
                                            .toCompletableFuture()
                                            .thenCompose(q -> {
                                                long endTime = System.currentTimeMillis();
                                                return CompletableFuture.completedFuture(endTime - startTime);
                                            });
                                }).toMat(fold, Keep.right());

                        Source.from(Collections.singletonList(r))
                                .toMat(testSink, Keep.right()).run(actorMaterializer);
                    })
        });
    }

    public static void main(String[] args) throws IOException {
        System.out.println("start!");
        ActorSystem system = ActorSystem.create("routes");
        final Http http = Http.get(system);
        final ActorMaterializer materializer = ActorMaterializer.create(system);
        final AsyncHttpClient asyncHttpClient = asyncHttpClient();
        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow =

        final CompletionStage<ServerBinding> binding = http.bindAndHandle(
                routeFlow,
                ConnectHttp.toHost(HOST_NAME, PORT),
                materializer
        );
        System.out.println("Server online at http://localhost:8080/\nPress RETURN to stop...");
        System.in.read();
        binding
                .thenCompose(ServerBinding::unbind)
                .thenAccept(unbound -> system.terminate());

        asyncHttpClient.close();
    }
}
