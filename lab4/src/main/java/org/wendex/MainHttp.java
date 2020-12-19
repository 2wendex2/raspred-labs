package org.wendex;

import akka.http.javadsl.server.Route;
import static akka.http.javadsl.server.Directives.*;

public class MainHttp {
    public Route getRoute() {
        return get(()-> {
            
        });
    }
}
