package org.wendex;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Test {
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

    @JsonCreator public Test()
}
