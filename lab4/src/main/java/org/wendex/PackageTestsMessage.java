package org.wendex;

import com.fasterxml.jackson.annotation.JsonAnyGetter;

import java.util.HashMap;

public class PackageTestsMessage {
    private HashMap<String, Boolean> testsResults;

    @JsonAnyGetter
    public HashMap<String, Boolean> getTestsResults() {
        return testsResults;
    }

    public PackageTestsMessage(HashMap<String, Boolean> testsResults) {
        this.testsResults = testsResults;
    }
}
