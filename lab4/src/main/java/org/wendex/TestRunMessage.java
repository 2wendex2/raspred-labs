package org.wendex;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TestRunMessage {
    @JsonProperty
    private int packageId;
    private String functionName;
    private String jsString;
    private Object[] params;
    private String testName;

    public TestResultMessage toTestResultMessage(Object testResult) {
        return new TestResultMessage(packageId, testName, testResult);
    }

    public String getJsString() {
        return jsString;
    }

    public Object[] getParams() {
        return params;
    }

    public String getFunctionName() {
        return functionName;
    }

    public TestRunMessage(int packageId, String functionName, String jsString, Object[] params, String testName) {
        this.packageId = packageId;
        this.functionName = functionName;
        this.jsString = jsString;
        this.params = params;
        this.testName = testName;
    }
}
