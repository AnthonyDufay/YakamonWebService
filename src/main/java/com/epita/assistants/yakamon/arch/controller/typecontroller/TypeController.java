package com.epita.assistants.yakamon.arch.controller.typecontroller;

import com.epita.assistants.yakamon.arch.Controller;
import com.epita.assistants.yakamon.arch.service.entity.TypeEntity;
import com.epita.assistants.yakamon.arch.service.typeservice.TypeService;

import java.sql.Connection;
import java.util.ArrayList;

@Controller
public class TypeController {

    private final TypeService typeService;
    private final Connection conn;

    public TypeController(final Connection conn) {
        this.conn = conn;
        typeService = new TypeService(conn);
    }

    public TypeResponse findType(final TypeRequest type) {
        final var result = typeService.findType(new TypeEntity(type.type));
        if (result == null)
            return objectNotFoundResponse();
        return new TypeResponse(result.name);
    }

    public ListTypeResponse getAllType() {
        final var result = typeService.getAllType();
        return new ListTypeResponse(result);
    }

    public TypeRequest request(String arg) {
        return new TypeRequest(arg);
    }

    public TypeResponse objectNotFoundResponse() {
        var r = new TypeResponse(null);
        r.status = "FAILURE";
        r.errorCode = "OBJECT_NOT_FOUND";
        r.errorMessage = "Object not found";
        return r;
    }

    public Connection getConn() {
        return conn;
    }
}
