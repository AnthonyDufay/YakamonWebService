package com.epita.assistants.yakamon.arch.controller.typecontroller;

import com.epita.assistants.yakamon.arch.DtoRequest;

@DtoRequest
public class TypeRequest {
    public final String type;

    public TypeRequest(String type) {
        this.type = type;
    }
}
