package org.wendex;

import java.util.HashMap;

public class PackageTestsMessage {
    private HashMap<String, Boolean> testsResults;

    public HashMap<String, Boolean> getTestsResults() {
        return testsResults;
    }

    public PackageTestsMessage(HashMap<String, Boolean> testsResults) {
        this.testsResults = testsResults;
    }
}
