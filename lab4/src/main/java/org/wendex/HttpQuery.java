package org.wendex;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HttpQuery {
    @JsonProperty("packageId")          private int packageId;
    @JsonProperty("functionName")       private String functionName;
    @JsonProperty("jsScript")           private String jsScript;
    @JsonProperty("params")             private Object[] params;
    @JsonProperty("testName")           private String testName;
}
