package org.wendex;

import akka.actor.ActorRef;
import akka.dispatch.OnComplete;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.server.Route;
import akka.pattern.Patterns;

import static akka.http.javadsl.server.Directives.*;

public class MainHttp {
    private static final String PROPERTY_PACKAGE_ID = "packageId";
    private static final int QUERY_TIMEOUT = 10000;

    public Route getRoute(ActorRef actor) {
        return post(() -> entity(Jackson.unmarshaller(HttpQuery.class), m -> {
            for (Test t : m.getTests()) {
                actor.tell(new TestRunMessage(m.getPackageId(), m.getFunctionName(), m.getJsScript(),
                        t.getParams(), t.getTestName(), t.getExpectedResult()), ActorRef.noSender());
            }
            return complete("SUCCESS");
        })).orElse(get(() -> parameter(PROPERTY_PACKAGE_ID, m -> {
            Patterns.ask(actor, new TestQueryMessage(Integer.parseInt(m)), QUERY_TIMEOUT)
                    .onComplete(new OnComplete<Object>() {
                    });

        })));
    }
}
