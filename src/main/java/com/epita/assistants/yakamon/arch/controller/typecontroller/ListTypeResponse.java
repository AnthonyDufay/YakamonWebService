package com.epita.assistants.yakamon.arch.controller.typecontroller;

import com.epita.assistants.yakamon.arch.DtoResponse;
import com.epita.assistants.yakamon.arch.service.entity.TypeEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@DtoResponse
public class ListTypeResponse{
    public String status = "SUCCESS";
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String errorCode = null;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String errorMessage = null;
    public final List<TypeEntity> types;

    public ListTypeResponse(List<TypeEntity> types) {
        this.types = types;
    }
}
