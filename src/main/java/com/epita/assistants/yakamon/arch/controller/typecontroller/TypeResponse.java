package com.epita.assistants.yakamon.arch.controller.typecontroller;

import com.epita.assistants.yakamon.arch.DtoResponse;
import com.fasterxml.jackson.annotation.JsonInclude;

@DtoResponse
public class TypeResponse {
    public String status = "SUCCESS";
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String errorCode = null;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String errorMessage = null;
    public final String name;

    public TypeResponse(String name) {
        this.name = name;
    }
}
