package com.epita.assistants.yakamon.arch.controller.trainercontroller;

import com.epita.assistants.yakamon.arch.DtoResponse;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.UUID;

@DtoResponse
public class TrainerResponse {
    public String status = "SUCCESS";
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String errorCode = null;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String errorMessage = null;

    public UUID uuid;
    public String name;

    public TrainerResponse(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }
}
