package com.epita.assistants.yakamon.arch.controller.zonecontroller;

import com.epita.assistants.yakamon.arch.Controller;
import com.epita.assistants.yakamon.arch.service.entity.ZoneEntity;
import com.epita.assistants.yakamon.arch.service.zoneservice.ZoneService;

import java.sql.Connection;
import java.util.ArrayList;

@Controller
public class ZoneController {
    private final ZoneService zoneService;
    private final Connection conn;

    public ZoneController(final Connection conn) {
        this.conn = conn;
        this.zoneService = new ZoneService(conn);
    }

    public ZoneService getZoneService() {
        return zoneService;
    }

    public Connection getConn() {
        return conn;
    }

    public ZoneResponse findZone(ZoneRequest req) {
        final var result = zoneService.findZone(new ZoneEntity(req.name));
        if (result == null)
            return objectNotFoundResponse();
        return new ZoneResponse(result.name);
    }

    public ListZoneResponse getAllZones() {
        final var result = zoneService.getAllZones();
        return new ListZoneResponse(result);
    }

    public ListUUIDResponse getCurrentYakamon(ZoneRequest req) {
        final var result = zoneService.getCurrentYakamon(new ZoneEntity(req.name));
        if (result == null)
            return objectNotFoundUUIDResponse();
        return new ListUUIDResponse(result);
    }

    public ListStringResponse getPossibleYakamon(ZoneRequest req) {
        final var result = zoneService.getPossibleYakamon(new ZoneEntity(req.name));
        if (result == null)
            return objectNotFoundStringResponse();
        return new ListStringResponse(result);
    }

    public ZoneResponse objectNotFoundResponse() {
        var r = new ZoneResponse(null);
        r.status = "FAILURE";
        r.errorCode = "OBJECT_NOT_FOUND";
        r.errorMessage = "Object not found";
        return r;
    }

    public ListStringResponse objectNotFoundStringResponse() {
        var r = new ListStringResponse(null);
        r.status = "FAILURE";
        r.errorCode = "OBJECT_NOT_FOUND";
        r.errorMessage = "Object not found";
        return r;
    }

    public ListUUIDResponse objectNotFoundUUIDResponse() {
        var r = new ListUUIDResponse(null);
        r.status = "FAILURE";
        r.errorCode = "OBJECT_NOT_FOUND";
        r.errorMessage = "Object not found";
        return r;
    }

    public ZoneRequest request(String arg) {
        return new ZoneRequest(arg);
    }
}
