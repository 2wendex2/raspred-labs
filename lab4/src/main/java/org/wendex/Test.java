package org.wendex;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Test {
    @JsonProperty("jsScript")           private String jsScript;
    @JsonProperty("params")             private Object[] params;
    @JsonProperty("tests")              private Test[] tests;
}
