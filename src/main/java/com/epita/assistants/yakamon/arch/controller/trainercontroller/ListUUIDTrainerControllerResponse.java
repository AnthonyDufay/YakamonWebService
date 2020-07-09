package com.epita.assistants.yakamon.arch.controller.trainercontroller;

import com.epita.assistants.yakamon.arch.DtoResponse;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.UUID;

@DtoResponse
public class ListUUIDTrainerControllerResponse {
    public String status = "SUCCESS";
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String errorCode = null;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String errorMessage = null;

    public List<UUID> trainerYakamonIds;

    public ListUUIDTrainerControllerResponse(List<UUID> trainerYakamonIds) {
        this.trainerYakamonIds = trainerYakamonIds;
    }
}
