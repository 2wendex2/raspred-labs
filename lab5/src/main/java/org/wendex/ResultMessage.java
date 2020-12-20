package org.wendex;

import akka.japi.Pair;

public class ResultMessage {
    private Long time;
    private Pair<String, Integer> p;

    public Long getTime() {
        return time;
    }

    public Pair<String, Integer> getPair() {
        return p;
    }

    public String getTestUrl() {
        return p.first();
    }

    public int getCount() {
        return p.second();
    }

    public ResultMessage(Long time, Pair<String, Integer> p) {
        this.time = time;
        this.p = p;
    }

    public ResultMessage(Long time, String testUrl, int count) {
        p = new Pair<>(testUrl, count);
        this.time = time;
    }
}
