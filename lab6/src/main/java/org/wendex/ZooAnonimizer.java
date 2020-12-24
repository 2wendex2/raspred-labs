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
import akka.http.javadsl.server.Route;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import org.apache.zookeeper.*;

import java.util.List;
import java.util.concurrent.CompletionStage;

public class ZooAnonimizer implements Watcher {
    static final int PORT_MAX = 65535;
    static final int ZOO_PORT = 2181;
    static final String PROPERTY_URL = "url";
    static final String PROPERTY_COUNT = "count";

    public static Route createRoute(ActorRef actor) {
        return get(())
                post(() -> entity(Jackson.unmarshaller(HttpQuery.class), m -> {
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

    public static void main(String[] args) throws Exception {
        if (args.length != 1)
            throw new IllegalArgumentException("Wrong arguments count");
        int port;
        try {
            port = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Port must be number", e);
        }
        if (port > PORT_MAX)
            throw new IllegalArgumentException("Port must be less then " + PORT_MAX);
        ZooAnonimizer anonimizer = new ZooAnonimizer(port);

        ActorSystem actorSystem = ActorSystem.create("test");
        ActorRef zooActor = actorSystem.actorOf(Props.create(ZooActor.class));
        final Http http = Http.get(actorSystem);
        final ActorMaterializer materializer = ActorMaterializer.create(actorSystem);
        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow =
                createRoute(testRouterActor).flow(actorSystem, materializer);
        final CompletionStage<ServerBinding> binding = http.bindAndHandle(
                routeFlow, ConnectHttp.toHost("localhost", 8080), materializer);
        System.in.read();
        binding.thenCompose(ServerBinding::unbind)
                .thenAccept(unbound -> actorSystem.terminate());
        System.in.read();
    }

    private ZooKeeper zoo;
    private int port;
    private String path;

    private static byte[] portToBytes(int port) {
        byte[] bytes = new byte[2];
        bytes[0] = (byte)port;
        bytes[1] = (byte)(port >>> 8);
        return bytes;
    }

    private static int bytesToPort(byte[] bytes) {
        return bytes[0] | (bytes[1] << 8);
    }

    public ZooAnonimizer(int port) throws Exception {
        this.port = port;
        this.zoo = new ZooKeeper("127.0.0.1:" + ZOO_PORT, 3000, this);
        this.path = "/servers/s" + port;
        zoo.create(path, portToBytes(port), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
    }

    private int[] getPortsList() throws Exception {
        List<String> servers = zoo.getChildren("/servers", this);
        int[] prts = new int[servers.size()];
        int i = 0;
        for (String s : servers) {
            prts[i] = bytesToPort(zoo.getData("/servers/" + s, false, null));
            i++;
        }
        return prts;
    }

    @Override
    public void process(WatchedEvent watchedEvent) {

    }
}
