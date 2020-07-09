package com.epita.assistants.yakamon.arch.repository.model;

import com.epita.assistants.yakamon.arch.Model;

import java.util.List;

@Model
public class YakadexModel {
    private String id;
    private String previousEvolutionYakadexId;
    private String nextEvolutionYakadexId;
    private List<String> moveIds;
    private List<String> typeIds;
    private List<String> zoneIds;

    public YakadexModel(String id, String previousEvolutionYakadexId, String nextEvolutionYakadexId,
                        List<String> moveIds, List<String> typeIds, List<String> zoneIds) {
        this.id = id;
        this.previousEvolutionYakadexId = previousEvolutionYakadexId;
        this.nextEvolutionYakadexId = nextEvolutionYakadexId;
        this.moveIds = moveIds;
        this.typeIds = typeIds;
        this.zoneIds = zoneIds;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPreviousEvolutionYakadexId() {
        return previousEvolutionYakadexId;
    }

    public void setPreviousEvolutionYakadexId(String previousEvolutionYakadexId) {
        this.previousEvolutionYakadexId = previousEvolutionYakadexId;
    }

    public String getNextEvolutionYakadexId() {
        return nextEvolutionYakadexId;
    }

    public void setNextEvolutionYakadexId(String nextEvolutionYakadexId) {
        this.nextEvolutionYakadexId = nextEvolutionYakadexId;
    }

    public List<String> getMoveIds() {
        return moveIds;
    }

    public void setMoveIds(List<String> moveIds) {
        this.moveIds = moveIds;
    }

    public List<String> getTypeIds() {
        return typeIds;
    }

    public void setTypeIds(List<String> typeIds) {
        this.typeIds = typeIds;
    }

    public List<String> getZoneIds() {
        return zoneIds;
    }

    public void setZoneIds(List<String> zoneIds) {
        this.zoneIds = zoneIds;
    }
}
