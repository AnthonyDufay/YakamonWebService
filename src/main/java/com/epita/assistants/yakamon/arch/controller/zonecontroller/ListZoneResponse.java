package com.epita.assistants.yakamon.arch.controller.zonecontroller;

import com.epita.assistants.yakamon.arch.DtoResponse;
import com.epita.assistants.yakamon.arch.service.entity.ZoneEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@DtoResponse
public class ListZoneResponse {
    public String status = "SUCCESS";
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String errorCode = null;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String errorMessage = null;
    public List<ZoneEntity> zones;

    public ListZoneResponse(List<ZoneEntity> zones) {
        this.zones = zones;
    }
}
