package org.wendex;

import akka.japi.Pair;

public class QueryMessage {
    private Pair<String, Integer> p;

    public int getCount() {
        return p.second();
    }

    public String getTestUrl() {
        return p.first();
    }

    public Pair<String, Integer> getPair() {
        return p;
    }

    public QueryMessage(Pair<String, Integer> p) {
        this.p = p;
    }

    public QueryMessage(String testUrl, int count) {
        p = new Pair<>(testUrl, count);
    }
}
