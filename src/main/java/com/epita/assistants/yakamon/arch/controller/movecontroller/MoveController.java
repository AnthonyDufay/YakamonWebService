package com.epita.assistants.yakamon.arch.controller.movecontroller;

import com.epita.assistants.yakamon.arch.Controller;
import com.epita.assistants.yakamon.arch.service.entity.MoveEntity;
import com.epita.assistants.yakamon.arch.service.moveservice.MoveService;

import java.sql.Connection;
import java.util.ArrayList;

@Controller
public class MoveController {

    private final MoveService moveService;
    private final Connection conn;

    public MoveController(final Connection conn) {
        this.conn = conn;
        moveService = new MoveService(conn);
    }

    public MoveResponse findMove(final MoveRequest move) {
        final var result = moveService.findMove(new MoveEntity(move.name));
        if (result == null)
            return objectNotFoundResponse();
        return new MoveResponse(move.name);
    }

    public ListMoveResponse getAllMove() {
        final var result = moveService.getAllMove();
        return new ListMoveResponse(result);
    }

    public MoveResponse objectNotFoundResponse() {
        var r = new MoveResponse(null);
        r.status = "FAILURE";;
        r.errorCode = "OBJECT_NOT_FOUND";
        r.errorMessage = "Object not found";
        return r;
    }

    public MoveRequest request(String arg) {
        return new MoveRequest(arg);
    }

    public Connection getConn() {
        return conn;
    }
}
