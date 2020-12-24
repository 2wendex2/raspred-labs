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
import akka.pattern.Patterns;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import org.apache.zookeeper.*;

import static akka.http.javadsl.server.Directives.*;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletionStage;

public class ZooAnonimizer implements Watcher {
    private static final int PORT_MAX = 65535;
    private static final int ZOO_PORT = 2181;
    private static final String PROPERTY_URL = "url";
    private static final String PROPERTY_COUNT = "count";
    private static final int QUERY_TIMEOUT = 10000;
    private static final String SERVERS_PATH = "/servers";

    public static Route createRoute(ActorRef actor, int port, Http http) {
        return get(() -> parameter(PROPERTY_URL, url -> parameter(PROPERTY_COUNT, countStr -> {
            int count = Integer.parseInt(countStr);
            if (count == 0) {
                System.out.println("RESULT FOR " + port);
                System.out.println();
                return completeWithFuture(http.singleRequest(HttpRequest.create(url)));
            }
            else {
                return completeWithFuture(Patterns.ask(actor, new ServerQueryMessage(), Duration.ofMillis(QUERY_TIMEOUT))
                        .thenCompose(m -> {
                            System.out.println("RECIEVE " + port);
                            System.out.println("COUNT " + count);
                            System.out.println();
                            ServerUrlMessage urlMessage = (ServerUrlMessage)m;
                            return http.singleRequest(HttpRequest.create(urlMessage.getUrl()));
                        }));
            }
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
        anonimizer.start();
    }

    private ZooKeeper zoo;
    private int port;
    private String path;
    private ActorRef actor;
    private ActorSystem actorSystem;
    private Http http;

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
        this.path = SERVERS_PATH + "/s" + port;
        zoo.create(path, portToBytes(port), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        actorSystem = ActorSystem.create("anonimizer");
        actor = actorSystem.actorOf(Props.create(ZooActor.class, getPortsList()));
        http = Http.get(actorSystem);
    }

    private int[] getPortsList() throws Exception {
        List<String> servers = zoo.getChildren(SERVERS_PATH, this);
        int[] prts = new int[servers.size()];
        int i = 0;
        for (String s : servers) {
            prts[i] = bytesToPort(zoo.getData(SERVERS_PATH + "/" + s, false, null));
            i++;
        }
        return prts;
    }

    private void start() throws Exception {
        final ActorMaterializer materializer = ActorMaterializer.create(actorSystem);
        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow =
                createRoute(actor, port).flow(actorSystem, materializer);
        final CompletionStage<ServerBinding> binding = http.bindAndHandle(
                routeFlow, ConnectHttp.toHost("localhost", port), materializer);
        System.in.read();
        binding.thenCompose(ServerBinding::unbind)
                .thenAccept(unbound -> actorSystem.terminate());
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        try {
            if (watchedEvent.getPath().equals(SERVERS_PATH) && watchedEvent.getType() == Event.EventType.NodeChildrenChanged) {
                actor.tell(new ServerListMessage(getPortsList()), ActorRef.noSender());
            }
        } catch (Exception e) {
            throw new RuntimeException("Process exception", e);
        }
    }
}
