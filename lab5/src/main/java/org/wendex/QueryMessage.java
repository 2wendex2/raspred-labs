package org.wendex;

public class QueryMessage {
    private String testUrl;
    private int count;

    public int getCount() {
        return count;
    }

    public String getTestUrl() {
        return testUrl;
    }

    public QueryMessage(String testUrl, int count) {
        this.testUrl = testUrl;
        this.count = count;
    }

}
