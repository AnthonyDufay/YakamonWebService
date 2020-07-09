package com.epita.assistants.yakamon.arch.controller.statscontroller;

import com.epita.assistants.yakamon.arch.DtoResponse;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

@DtoResponse
public class ListDistributionResponse {
    public String status = "SUCCESS";
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String errorCode = null;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String errorMessage = null;

    public Map<String, Map<String, Float>> distributions;

    public ListDistributionResponse(Map<String, Map<String, Float>> distributions) {
        this.distributions = distributions;
    }
}
