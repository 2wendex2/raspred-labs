package org.wendex;

import akka.http.javadsl.server.Route;

public class MainHttp {
    public Route getRoute() {
        return get();
    }
}
