package com.epita.assistants.yakamon.arch.controller.yakadexcontroller;

import com.epita.assistants.yakamon.arch.DtoResponse;
import com.epita.assistants.yakamon.arch.service.entity.YakadexEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@DtoResponse
public class ListYakadexResponse {
    public String status = "SUCCESS";
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String errorCode = null;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String errorMessage = null;
    final public List<YakadexEntity> yakadexEntries;

    public ListYakadexResponse(final List<YakadexEntity> yakadexEntries) {
        this.yakadexEntries = yakadexEntries;
    }
}
