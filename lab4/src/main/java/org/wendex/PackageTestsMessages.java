package org.wendex;

import java.util.HashMap;

public class PackageTestsMessages {
    private HashMap<String, Object> testsResults;

    public HashMap<String, Object> getTestsResults() {
        return testsResults;
    }

    public PackageTestsMessages(HashMap<String, Object> testsResults) {
        this.testsResults = testsResults;
    }
}
