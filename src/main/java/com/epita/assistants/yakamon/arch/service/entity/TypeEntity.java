package com.epita.assistants.yakamon.arch.service.entity;

import com.epita.assistants.yakamon.arch.Entity;

@Entity
public class TypeEntity {
    public String name;

    public TypeEntity(String type) {
        this.name = type;
    }
}
