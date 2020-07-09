package com.epita.assistants.yakamon.arch.controller.statscontroller;

import com.epita.assistants.yakamon.arch.Controller;
import com.epita.assistants.yakamon.arch.controller.zonecontroller.ZoneRequest;
import com.epita.assistants.yakamon.arch.service.entity.ZoneEntity;
import com.epita.assistants.yakamon.arch.service.statsservice.StatsService;

import java.sql.Connection;

@Controller
public class StatsController {
    final private StatsService statsService;
    final private Connection conn;

    public StatsController(final Connection conn) {
        this.conn = conn;
        this.statsService = new StatsService(conn);
    }

    public Connection getConn() {
        return conn;
    }

    public StatsService getStatsService() {
        return statsService;
    }

    public ListDistributionResponse getAllDistribution() {
        return new ListDistributionResponse(statsService.getAllDistribution());
    }

    public ZoneDistributionResponse getZoneDistribution(ZoneRequest zone) {
        var result = statsService.getZoneDistribution(new ZoneEntity(zone.name));
        if (result == null) {
            return objectNotFoundResponse();
        }
        return new ZoneDistributionResponse(result);
    }

    public ListStringStatsResponse getTopNb(Integer topNb) {
        if (topNb == null)
            return badParameterResponse();
        var r = statsService.getTopNb(topNb);
        if (r == null)
            return badParameterResponse();
        return new ListStringStatsResponse(r);
    }

    public Integer topNbRequest(String topNb) {
        try {
            return Integer.parseInt(topNb);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public ListStringStatsResponse badParameterResponse() {
        var r = new ListStringStatsResponse(null);
        r.status = "FAILURE";
        r.errorCode = "BAD_PARAMETER";
        r.errorMessage = "Bad parameter";
        return r;
    }

    public ZoneDistributionResponse objectNotFoundResponse() {
        var r = new ZoneDistributionResponse(null);
        r.status = "FAILURE";
        r.errorCode = "OBJECT_NOT_FOUND";
        r.errorMessage = "Object not found";
        return r;
    }
}
