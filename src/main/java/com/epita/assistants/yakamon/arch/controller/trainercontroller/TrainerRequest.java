package com.epita.assistants.yakamon.arch.controller.trainercontroller;

import com.epita.assistants.yakamon.arch.DtoRequest;

import java.util.UUID;

@DtoRequest
public class TrainerRequest {
    public UUID uuid;
    public String name;

    public TrainerRequest(UUID uuid) {
        this.uuid = uuid;
        this.name = null;
    }

    public TrainerRequest(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }
}
