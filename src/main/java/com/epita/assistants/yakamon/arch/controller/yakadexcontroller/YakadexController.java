package com.epita.assistants.yakamon.arch.controller.yakadexcontroller;

import com.epita.assistants.yakamon.arch.Controller;
import com.epita.assistants.yakamon.arch.service.entity.YakadexEntity;
import com.epita.assistants.yakamon.arch.service.yakadexservice.YakadexService;

import java.sql.Connection;
import java.util.ArrayList;

@Controller
public class YakadexController {
    private final YakadexService yakadexService;
    private final Connection conn;

    public YakadexController(final Connection conn) {
        this.conn = conn;
        this.yakadexService = new YakadexService(conn);
    }

    public Connection getConn() {
        return conn;
    }

    public YakadexService getYakadexService() {
        return yakadexService;
    }

    public YakadexResponse findYakadexEntrie(YakadexRequest req) {
        final var result = yakadexService.findYakadexEntrie(new YakadexEntity(req.id, null,
                null, null, null, null));
        if  (result == null)
            return objectNotFoundResponse();
        return new YakadexResponse(result.id, result.previousEvolutionYakadexId, result.nextEvolutionYakadexId,
                result.moveIds, result.typeIds, result.zoneIds);
    }

    public ListYakadexResponse getAllYakadexEntries() {
        final var result = yakadexService.getAllYakadexEntries();
        return new ListYakadexResponse(result);
    }

    public YakadexResponse objectNotFoundResponse() {
        var r = new YakadexResponse(null, null, null, null,
                null, null);
        r.status = "FAILURE";
        r.errorCode = "OBJECT_NOT_FOUND";
        r.errorMessage = "Object not found";
        return r;
    }

    public YakadexRequest request(String arg) {
        return new YakadexRequest(arg);
    }
}
