package org.wendex;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Test {
    @JsonProperty("testName")           private String testName;
    @JsonProperty("params")             private Object[] params;
    @JsonProperty("expectedResult")     private Test[] tests;
}
