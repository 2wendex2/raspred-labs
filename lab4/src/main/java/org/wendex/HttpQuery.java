package org.wendex;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class HttpQuery {
    private static final String PROPERTY_PACKAGE_ID = "packageId";
    private static final String PROPERTY_FUNCTION_NAME = "functionName";
    private static final String PROPERTY_JS_SCRIPT = "jsScript";
    private static final String PROPERTY_TESTS = "tests";

    @JsonProperty(PROPERTY_PACKAGE_ID)          private int packageId;
    @JsonProperty(PROPERTY_FUNCTION_NAME)       private String functionName;
    @JsonProperty(PROPERTY_JS_SCRIPT)           private String jsScript;
    @JsonProperty(PROPERTY_TESTS)               private Test[] tests;

    public String getFunctionName() {
        return functionName;
    }

    public int getPackageId() {
        return packageId;
    }

    public String getJsScript() {
        return jsScript;
    }

    public Test[] getTests() {
        return tests;
    }

    @JsonCreator public HttpQuery(@JsonProperty(PROPERTY_PACKAGE_ID)          int packageId,
                                  @JsonProperty(PROPERTY_FUNCTION_NAME)       String functionName,
                                  @JsonProperty(PROPERTY_JS_SCRIPT)           String jsScript,
                                  @JsonProperty(PROPERTY_TESTS)               Test[] tests) {
        this.packageId = packageId;
        this.functionName = functionName;
        this.jsScript = jsScript;
        this.tests = tests;
    }
}
