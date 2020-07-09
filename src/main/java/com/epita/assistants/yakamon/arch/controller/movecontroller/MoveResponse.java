package com.epita.assistants.yakamon.arch.controller.movecontroller;

import com.epita.assistants.yakamon.arch.DtoResponse;
import com.fasterxml.jackson.annotation.JsonInclude;

@DtoResponse
public class MoveResponse {
    public String status = "SUCCESS";
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String errorCode = null;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String errorMessage = null;
    public final String name;

    public MoveResponse(String name) {
        this.name = name;
    }
}
