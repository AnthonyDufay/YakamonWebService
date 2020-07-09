package com.epita.assistants.yakamon.arch.controller.statscontroller;

import com.epita.assistants.yakamon.arch.DtoResponse;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

@DtoResponse
public class ZoneDistributionResponse {
    public String status = "SUCCESS";
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String errorCode = null;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String errorMessage = null;

    public Map<String, Float> distribution;

    public ZoneDistributionResponse(Map<String, Float> distribution) {
        this.distribution = distribution;
    }
}
