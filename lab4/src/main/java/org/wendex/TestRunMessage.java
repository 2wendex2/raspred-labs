package org.wendex;


public class TestRunMessage {
    private int packageId;
    private String functionName;
    private String jsString;
    private Object[] params;
    private String testName;
    private Object testResult;

    public String getJsString() {
        return jsString;
    }

    public Object[] getParams() {
        return params;
    }

    public int getPackageId() {
        return packageId;
    }

    public String getTestName() {
        return testName;
    }

    public Object getTestResult() {
        return testResult;
    }

    public String getFunctionName() {
        return functionName;
    }

    public TestRunMessage(int packageId, String functionName, String jsString, Object[] params, String testName,
                          Object testResult) {
        this.packageId = packageId;
        this.functionName = functionName;
        this.jsString = jsString;
        this.params = params;
        this.testName = testName;
        this.testResult = testResult;
    }
}
