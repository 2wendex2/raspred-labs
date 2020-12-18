package org.wendex;

public class TestRunMessage {
    private int packageId;
    private String functionName;
    private String jsString;
    private Object[] params;

    public int getPackageId() {
        return packageId;
    }

    public String getFunctionName() {
        return functionName;
    }

    public String getJsString() {
        return jsString;
    }

    public Object[] getParams() {
        return params;
    }

    public TestRunMessage(int packageId, String functionName, String jsString, Object[] params) {
        this.packageId = packageId;
        this.functionName = functionName;
        this.jsString = jsString;
        this.params = params;
    }
}
