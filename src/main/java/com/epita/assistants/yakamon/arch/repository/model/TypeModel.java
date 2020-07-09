package com.epita.assistants.yakamon.arch.repository.model;

import com.epita.assistants.yakamon.arch.Model;

@Model
public class TypeModel {
    String type;

    public void setType(String type) {
        this.type = type;
    }

    public TypeModel(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
