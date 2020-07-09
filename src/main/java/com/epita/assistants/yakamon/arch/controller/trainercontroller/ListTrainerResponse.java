package com.epita.assistants.yakamon.arch.controller.trainercontroller;

import com.epita.assistants.yakamon.arch.DtoResponse;
import com.epita.assistants.yakamon.arch.service.entity.TrainerEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@DtoResponse
public class ListTrainerResponse {
    public String status = "SUCCESS";
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String errorCode = null;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String errorMessage = null;

    public List<TrainerEntity> trainers;

    public ListTrainerResponse(List<TrainerEntity> trainers) {
        this.trainers = trainers;
    }
}
