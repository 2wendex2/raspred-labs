package org.wendex;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Test {
    static final String PROPERTY_TEST_NAME =

    @JsonProperty("testName")           private String testName;
    @JsonProperty("params")             private Object[] params;
    @JsonProperty("expectedResult")     private Object expectedResult;

    public String getTestName() {
        return testName;
    }

    public Object getExpectedResult() {
        return expectedResult;
    }

    public Object[] getParams() {
        return params;
    }

    @JsonCreator public Test(@JsonProperty("testName")          String testName,
                             @JsonProperty("params")            Object[] params,
                             @JsonProperty("expectedResult")    Object expectedResult) {
        this.testName = testName;
        this.params = params;
        this.expectedResult = expectedResult;
    }
}
