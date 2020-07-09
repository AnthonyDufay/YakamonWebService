package com.epita.assistants.yakamon.arch.controller.movecontroller;

import com.epita.assistants.yakamon.arch.DtoResponse;
import com.epita.assistants.yakamon.arch.service.entity.MoveEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@DtoResponse
public class ListMoveResponse {
    public ListMoveResponse(List<MoveEntity> moves) {
        this.moves = moves;
    }

    public String status = "SUCCESS";
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String errorCode = null;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String errorMessage = null;
    public final List<MoveEntity> moves;
}
