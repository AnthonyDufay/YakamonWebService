package com.epita.assistants.yakamon.arch.controller.yakadexcontroller;

import com.epita.assistants.yakamon.arch.DtoResponse;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@DtoResponse
public class YakadexResponse {
    public String status = "SUCCESS";
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String errorCode = null;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String errorMessage = null;
    public String id;
    public String previousEvolutionYakadexId;
    public String nextEvolutionYakadexId;
    public List<String> moveIds;
    public List<String> typeIds;
    public List<String> zoneIds;

    public YakadexResponse(String id, String previousEvolutionYakadexId, String nextEvolutionYakadexId,
                          List<String> moveIds, List<String> typeIds, List<String> zoneIds) {
        this.id = id;
        this.previousEvolutionYakadexId = previousEvolutionYakadexId;
        this.nextEvolutionYakadexId = nextEvolutionYakadexId;
        this.moveIds = moveIds;
        this.typeIds = typeIds;
        this.zoneIds = zoneIds;
    }
}
