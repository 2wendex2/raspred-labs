package org.wendex;

public class TestRunMessage {
    private int packageId;
    private String functionName;
    private String jsString;
    private

    public int getPackageId() {
        return packageId;
    }

    public String getFunctionName() {
        return functionName;
    }

    public String getJsString() {
        return jsString;
    }

    public TestRunMessage(int packageId, String functionName, String jsString) {
        this.packageId = packageId;
        this.functionName = functionName;
        this.jsString = jsString;
    }
}
