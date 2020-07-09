package com.epita.assistants.yakamon.arch.controller.zonecontroller;

import com.epita.assistants.yakamon.arch.DtoRequest;

@DtoRequest
public class ZoneRequest {
    public String status = "SUCCESS";
    public String errorCode = null;
    public String errorMessage = null;

    public String name;


    public ZoneRequest(String name) {
        this.name = name;
    }
}
