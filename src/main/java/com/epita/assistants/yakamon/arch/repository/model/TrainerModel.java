package com.epita.assistants.yakamon.arch.repository.model;

import com.epita.assistants.yakamon.arch.Model;

import java.util.UUID;

@Model
public class TrainerModel {
    private UUID uuid;
    private String name;

    public TrainerModel(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
