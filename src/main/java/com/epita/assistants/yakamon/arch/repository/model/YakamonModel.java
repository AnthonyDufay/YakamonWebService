package com.epita.assistants.yakamon.arch.repository.model;

import com.epita.assistants.yakamon.arch.Model;

import java.util.UUID;

@Model
public class YakamonModel {
    private UUID uuid;
    private String name;
    private String YakadexId;

    public YakamonModel(UUID uuid, String name, String yakadexId) {
        this.uuid = uuid;
        this.name = name;
        YakadexId = yakadexId;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYakadexId() {
        return YakadexId;
    }

    public void setYakadexId(String yakadexId) {
        YakadexId = yakadexId;
    }
}
