package org.wendex;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Test {
    private static final String PROPERTY_TEST_NAME = "testName";
    private static final String PROPERTY_PARAMS = "params";
    private static final String PROPERTY_EXPECTED_RESULT = "expectedResult";

    @JsonProperty(PROPERTY_TEST_NAME)           private String testName;
    @JsonProperty(PROPERTY_PARAMS)              private Object[] params;
    @JsonProperty(PROPERTY_EXPECTED_RESULT)     private Object expectedResult;

    public String getTestName() {
        return testName;
    }

    public Object getExpectedResult() {
        return expectedResult;
    }

    public Object[] getParams() {
        return params;
    }

    @JsonCreator public Test(@JsonProperty(PROPERTY_TEST_NAME)          String testName,
                             @JsonProperty(PROPERTY_PARAMS)             Object[] params,
                             @JsonProperty(PROPERTY_EXPECTED_RESULT)    Object expectedResult) {
        this.testName = testName;
        this.params = params;
        this.expectedResult = expectedResult;
    }
}
