package com.epita.assistants.yakamon.arch.controller.yakamoncontroller;

import com.epita.assistants.yakamon.arch.Controller;
import com.epita.assistants.yakamon.arch.service.entity.YakamonEntity;
import com.epita.assistants.yakamon.arch.service.entity.ZoneEntity;
import com.epita.assistants.yakamon.arch.service.yakamonservice.YakamonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jooq.tools.json.JSONObject;
import org.jooq.tools.json.JSONParser;
import org.jooq.tools.json.ParseException;

import java.sql.Connection;
import java.util.UUID;

@Controller
public class YakamonController {
    final private YakamonService yakamonService;
    final private Connection conn;

    public YakamonController(final Connection conn) {
        this.conn = conn;
        this.yakamonService = new YakamonService(conn);
    }

    public Connection getConn() {
        return conn;
    }

    public YakamonService getYakamonService() {
        return yakamonService;
    }

    public ListYakamonResponse getAllYakamon() {
        final var result = yakamonService.getAllYakamon();
        return new ListYakamonResponse(result);
    }

    public YakamonResponse findYakamon(YakamonRequest req) {
        if (req == null)
            return badParameterResponse();
        final var result = yakamonService.findYakamon(new YakamonEntity(req.uuid, null, null));
        if (result == null)
            return objectNotFoundResponse();
        return new YakamonResponse(result.id, result.name, result.yakadexId);
    }

    public YakamonWithZoneResponse putYakamon(YakamonRequest req) {
        if (req == null || req.yakadexId == null || req.uuid == null || req.zoneId == null) {
            var r = new YakamonWithZoneResponse(null, null, null, null);
            r.status = "FAILURE";
            r.errorCode = "BAD_BODY";
            r.errorMessage = "Bad body";
            return r;
        }
        final var result = yakamonService.putYakamon(new YakamonEntity(req.uuid, req.name, req.yakadexId),
                new ZoneEntity(req.zoneId));
        if (result == null)
            return putFailedResponse();
        if (result.name != null && result.name.equals("BAD_BODY")) {
            var r = new YakamonWithZoneResponse(null, null, null, null);
            r.status = "FAILURE";
            r.errorCode = "BAD_BODY";
            r.errorMessage = "Bad body";
            return r;
        }
        return new YakamonWithZoneResponse(result.id, result.name, result.yakadexId, req.zoneId);
    }

    public YakamonResponse changeYakamonName(YakamonRequest req) {
        if (req == null)
            return badParameterResponse();
        if (req.name != null && req.name.equals("BAD_BODY"))
            return badBodyResponse();
        final var result = yakamonService.changeYakamonName(new YakamonEntity(req.uuid, req.name, req.yakadexId));
        if (result == null)
            return updateFailedResponse();
        if (result.name != null && result.name.equals("BAD_PARAMETER"))
            return badParameterResponse();
        return new YakamonResponse(result.id, result.name, result.yakadexId);
    }

    public YakamonResponse evolveYakamon(YakamonRequest req) {
        if (req == null)
            return badParameterResponse();
        final var result = yakamonService.evolveYakamon(new YakamonEntity(req.uuid, req.name, req.yakadexId));
        if (result == null)
            return updateFailedResponse();
        else if (result.name != null && result.name.equals("BAD_PARAMETER"))
            return badParameterResponse();
        else if (result.name != null && result.name.equals("UPDATE_FAILED"))
            return updateFailedResponse();
        return new YakamonResponse(result.id, result.name, result.yakadexId);
    }

    public EmptyYakamonResponse deleteYakamon(UUID req) {
        if (req == null) {
            var r = new EmptyYakamonResponse();
            r.status = "FAILURE";
            r.errorCode = "BAD_PARAMETER";
            r.errorMessage = "Bad parameter";
            return r;
        }
        final var result = yakamonService.deleteYakamon(req);
        if (!result) {
            var r = new EmptyYakamonResponse();
            r.status = "FAILURE";
            r.errorCode = "DELETION_FAILED";
            r.errorMessage = "Deletion failed";
            return r;
        }
        return new EmptyYakamonResponse();
    }

    public YakamonRequest request(String arg) {
        try {
            return new YakamonRequest(UUID.fromString(arg));
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public YakamonRequest putRequest(String arg) {
        ObjectMapper om = new ObjectMapper();
        var lol = new JSONParser();
        try {
            JSONObject obj = (JSONObject) lol.parse(arg);
            if (obj.size() > 4)
                return null;
            UUID uuid = UUID.fromString((String) obj.get("id"));
            return new YakamonRequest(uuid, (String) obj.get("name"), (String) obj.get("yakadexId"),
                (String) obj.get("zoneId"));
        } catch (Exception e) {
            return null;
        }
    }

    public YakamonRequest patchRenameRequest(String body, String id) {
        ObjectMapper om = new ObjectMapper();
        var parser = new JSONParser();
        try {
            UUID uuidObj = UUID.fromString(id);
            JSONObject obj = (JSONObject) parser.parse(body);
            if (obj.size() > 1)
                return new YakamonRequest((UUID) null, "BAD_BODY", null);
            return new YakamonRequest(uuidObj, (String) obj.get("newName"), null);
        } catch (ParseException e) {
            return new YakamonRequest((UUID) null, "BAD_BODY", null);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public UUID deletionRequest(String uuid) {
        try {
            return UUID.fromString(uuid);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public YakamonResponse deletionFailedResponse() {
        var r = new YakamonResponse(null, null, null);
        r.status = "FAILURE";
        r.errorCode = "DELETION_FAILED";
        r.errorMessage = "Deletion failed";
        return r;
    }

    public YakamonResponse updateFailedResponse() {
        var r = new YakamonResponse(null, null, null);
        r.status = "FAILURE";
        r.errorCode = "UPDATE_FAILED";
        r.errorMessage = "Update failed";
        return r;
    }

    public YakamonResponse badParameterResponse() {
        var r = new YakamonResponse(null, null, null);
        r.status = "FAILURE";
        r.errorCode = "BAD_PARAMETER";
        r.errorMessage = "Bad parameter";
        return r;
    }

    public YakamonResponse badBodyResponse() {
        var r = new YakamonResponse(null, null, null);
        r.status = "FAILURE";
        r.errorCode = "BAD_BODY";
        r.errorMessage = "Bad body";
        return r;
    }

    public YakamonWithZoneResponse putFailedResponse() {
        var r = new YakamonWithZoneResponse(null, null, null, null);
        r.status = "FAILURE";
        r.errorCode = "CREATION_FAILED";
        r.errorMessage = "Object creation failed";
        return r;
    }

    public YakamonResponse objectNotFoundResponse() {
        var r = new YakamonResponse(null, null, null);
        r.status = "FAILURE";
        r.errorCode = "OBJECT_NOT_FOUND";
        r.errorMessage = "Object not found";
        return r;
    }
}
