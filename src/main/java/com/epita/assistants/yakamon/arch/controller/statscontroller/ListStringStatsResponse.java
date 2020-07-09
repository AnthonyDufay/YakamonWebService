package com.epita.assistants.yakamon.arch.controller.statscontroller;

import com.epita.assistants.yakamon.arch.DtoResponse;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@DtoResponse
public class ListStringStatsResponse {
    public String status = "SUCCESS";
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String errorCode = null;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String errorMessage = null;

    public List<String> yakadexIds;

    public ListStringStatsResponse(List<String> yakadexIds) {
        this.yakadexIds = yakadexIds;
    }
}
