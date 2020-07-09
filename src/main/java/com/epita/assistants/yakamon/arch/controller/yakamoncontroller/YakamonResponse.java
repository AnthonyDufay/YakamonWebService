package com.epita.assistants.yakamon.arch.controller.yakamoncontroller;

import com.epita.assistants.yakamon.arch.DtoResponse;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.UUID;

@DtoResponse
public class YakamonResponse {
    public String status = "SUCCESS";
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String errorCode = null;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String errorMessage = null;

    public UUID id;
    public String name;
    public String yakadexId;

    public YakamonResponse(UUID id, String name, String yakadexId) {
        this.id = id;
        this.name = name;
        this.yakadexId = yakadexId;
    }
}
