package org.wendex;

import java.util.HashMap;

public class PackageTestsMessage {
    private HashMap<String, Object> testsResults;

    public HashMap<String, Object> getTestsResults() {
        return testsResults;
    }

    public PackageTestsMessage(HashMap<String, Object> testsResults) {
        this.testsResults = testsResults;
    }
}
