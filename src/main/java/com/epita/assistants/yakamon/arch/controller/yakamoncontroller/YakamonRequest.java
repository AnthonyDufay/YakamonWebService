package com.epita.assistants.yakamon.arch.controller.yakamoncontroller;

import com.epita.assistants.yakamon.arch.DtoRequest;

import java.util.UUID;

@DtoRequest
public class YakamonRequest {
    public UUID uuid;
    public String name;
    public String yakadexId;
    public String zoneId = null;

    public YakamonRequest(String uuid, String name, String yakadexId, String zoneId) {
        this.uuid = UUID.fromString(uuid);
        this.name = name;
        this.yakadexId = yakadexId;
        this.zoneId = zoneId;
    }

    public YakamonRequest(UUID uuid) {
        this.uuid = uuid;
        this.name = null;
        this.yakadexId = null;
    }

    public YakamonRequest(String uuid, String name, String yakadexId) {
        this.uuid = UUID.fromString(uuid);
        this.name = name;
        this.yakadexId = yakadexId;
    }

    public YakamonRequest(UUID uuid, String name, String yakadexId) {
        this.uuid = uuid;
        this.name = name;
        this.yakadexId = yakadexId;
    }

    public YakamonRequest(UUID uuid, String name, String yakadexId, String zoneId) {
        this.uuid = uuid;
        this.name = name;
        this.yakadexId = yakadexId;
        this.zoneId = zoneId;
    }
}
