package com.epita.assistants.yakamon.arch.service.entity;

import com.epita.assistants.yakamon.arch.Entity;

import java.util.List;

@Entity
public class YakadexEntity {
    public String id;
    public String previousEvolutionYakadexId;
    public String nextEvolutionYakadexId;
    public List<String> moveIds;
    public List<String> typeIds;
    public List<String> zoneIds;

    public YakadexEntity(String id, String previousEvolutionYakadexId, String nextEvolutionYakadexId,
                         List<String> moveIds, List<String> typeIds, List<String> zoneIds) {
        this.id = id;
        this.previousEvolutionYakadexId = previousEvolutionYakadexId;
        this.nextEvolutionYakadexId = nextEvolutionYakadexId;
        this.moveIds = moveIds;
        this.typeIds = typeIds;
        this.zoneIds = zoneIds;
    }
}
