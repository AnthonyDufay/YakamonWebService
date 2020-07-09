package com.epita.assistants.yakamon.arch.controller.movecontroller;

import com.epita.assistants.yakamon.arch.DtoRequest;

@DtoRequest
public class MoveRequest {
    public final String name;

    public MoveRequest(String name) {
        this.name = name;
    }
}
