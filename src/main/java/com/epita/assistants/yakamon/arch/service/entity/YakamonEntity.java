package com.epita.assistants.yakamon.arch.service.entity;

import com.epita.assistants.yakamon.arch.Entity;

import java.util.UUID;

@Entity
public class YakamonEntity {
    public UUID id;
    public String name;
    public String yakadexId;

    public YakamonEntity(UUID id, String name, String yakadexId) {
        this.id = id;
        this.name = name;
        this.yakadexId = yakadexId;
    }
}
