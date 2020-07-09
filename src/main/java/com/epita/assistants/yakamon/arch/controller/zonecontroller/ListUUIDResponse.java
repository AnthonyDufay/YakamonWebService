package com.epita.assistants.yakamon.arch.controller.zonecontroller;

import com.epita.assistants.yakamon.arch.DtoResponse;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.UUID;

@DtoResponse
public class ListUUIDResponse {
    public String status = "SUCCESS";
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String errorCode = null;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String errorMessage = null;
    public List<UUID> yakamonIds;

    public ListUUIDResponse(List<UUID> yakamonIds) {
        this.yakamonIds = yakamonIds;
    }
}
