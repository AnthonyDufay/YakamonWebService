package com.epita.assistants.yakamon.arch.controller.zonecontroller;

import com.epita.assistants.yakamon.arch.DtoResponse;
import com.fasterxml.jackson.annotation.JsonInclude;

@DtoResponse
public class ZoneResponse {
    public String status = "SUCCESS";
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String errorCode = null;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String errorMessage = null;

    public String name;


    public ZoneResponse(String name) {
        this.name = name;
    }
}
