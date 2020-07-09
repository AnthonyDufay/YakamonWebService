package com.epita.assistants.yakamon.arch.controller.yakamoncontroller;

import com.epita.assistants.yakamon.arch.DtoResponse;
import com.epita.assistants.yakamon.arch.service.entity.YakamonEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@DtoResponse
public class ListYakamonResponse {
    public String status = "SUCCESS";
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String errorCode = null;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String errorMessage = null;

    public List<YakamonEntity> yakamons;

    public ListYakamonResponse(List<YakamonEntity> yakamons) {
        this.yakamons = yakamons;
    }
}
