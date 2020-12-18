package org.wendex;

public class TestResultMessage {
    private int packageId;
    private String testName;
    private Object testResult;

    public TestResultMessage(int packageId, String testName, Object testResult) {
        this.packageId = packageId;
        this.testName = testName;
        this.testResult = testResult;
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
}
