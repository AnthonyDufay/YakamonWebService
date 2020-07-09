package com.epita.assistants.yakamon.arch.controller.yakadexcontroller;

import com.epita.assistants.yakamon.arch.DtoRequest;

@DtoRequest
public class YakadexRequest {
    public YakadexRequest(String id) {
        this.id = id;
    }

    public String id;
}
