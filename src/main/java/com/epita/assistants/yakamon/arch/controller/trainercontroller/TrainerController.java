package com.epita.assistants.yakamon.arch.controller.trainercontroller;

import com.epita.assistants.yakamon.arch.Controller;
import com.epita.assistants.yakamon.arch.controller.yakamoncontroller.YakamonRequest;
import com.epita.assistants.yakamon.arch.controller.zonecontroller.ZoneRequest;
import com.epita.assistants.yakamon.arch.service.entity.TrainerEntity;
import com.epita.assistants.yakamon.arch.service.entity.YakamonEntity;
import com.epita.assistants.yakamon.arch.service.entity.ZoneEntity;
import com.epita.assistants.yakamon.arch.service.trainerservice.TrainerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jooq.tools.json.JSONObject;
import org.jooq.tools.json.JSONParser;
import org.jooq.tools.json.ParseException;

import java.sql.Connection;
import java.util.UUID;

@Controller
public class TrainerController {
    final private TrainerService trainerService;
    final private Connection conn;

    public TrainerController(final Connection conn) {
        this.conn = conn;
        this.trainerService = new TrainerService(conn);
    }

    public Connection getConn() {
        return conn;
    }

    public TrainerService getTrainerService() {
        return trainerService;
    }

    public ListTrainerResponse getAllTrainer() {
        final var result = trainerService.getAllTrainer();
        return new ListTrainerResponse(result);
    }

    public TrainerResponse findTrainer(TrainerRequest req) {
        if (req == null)
            return badParameterResponse();
        final var result = trainerService.findTrainer(new TrainerEntity(req.uuid, req.name));
        if (result == null)
            return objectNotFoundResponse();
        return new TrainerResponse(result.uuid, result.name);
    }

    public ListUUIDTrainerControllerResponse getYakamonsTrainer(TrainerRequest req) {
        if (req == null)
            return badParameterUUIDResponse();
        final var result = trainerService.getYakamonsTrainer(new TrainerEntity(req.uuid, req.name));
        if (result == null)
            return objectNotFoundUUIDResponse();
        return new ListUUIDTrainerControllerResponse(result);
    }

    public TrainerResponse createTrainer(TrainerRequest req) {
        if (req.name.equals("BAD_BODY"))
            return badBodyResponse();
        final var result = trainerService.createTrainer(new TrainerEntity(req.uuid, req.name));
        if (result == null)
            return objectCreationFailed();
        return new TrainerResponse(result.uuid, result.name);
    }

    public EmptyTrainerResponse renameTrainer(TrainerRequest req) {
        if (req.name.equals("BAD_PARAMETER")) {
            var r = new EmptyTrainerResponse();
            r.status = "FAILURE";
            r.errorCode = "BAD_PARAMETER";
            r.errorMessage = "Bad parameter";
            return r;
        } else if (req.name.equals("BAD_BODY")) {
            var r = new EmptyTrainerResponse();
            r.status = "FAILURE";
            r.errorCode = "BAD_BODY";
            r.errorMessage = "Bad body";
            return r;
        }
        final var result = trainerService.renameTrainer(new TrainerEntity(req.uuid, req.name));
        if (result == null) {
            var r = new EmptyTrainerResponse();
            r.status = "FAILURE";
            r.errorCode = "UPDATE_FAILED";
            r.errorMessage = "Update failed";
            return r;
        }
        return new EmptyTrainerResponse();
    }

    public EmptyTrainerResponse deleteTrainer(TrainerRequest req) {
        if (req == null) {
            var r = new EmptyTrainerResponse();
            r.status = "FAILURE";
            r.errorCode = "BAD_PARAMETER";
            r.errorMessage = "Bad parameter";
            return r;
        }
        if (trainerService.deleteTrainer(new TrainerEntity(req.uuid, req.name)))
            return new EmptyTrainerResponse();
        var r = new EmptyTrainerResponse();
        r.status = "FAILURE";
        r.errorCode = "DELETION_FAILED";
        r.errorMessage = "Deletion failed";
        return r;
    }

    public EmptyTrainerResponse addYakamonToTrainer(TrainerRequest trainer, YakamonRequest yakamon) {
        if (trainer == null || yakamon == null) {
            var r = new EmptyTrainerResponse();
            r.status = "FAILURE";
            r.errorCode = "BAD_PARAMETER";
            r.errorMessage = "Bad parameter";
            return r;
        }
        if (trainerService.addYakamonToTrainer(new TrainerEntity(trainer.uuid, trainer.name),
                new YakamonEntity(yakamon.uuid, yakamon.name, yakamon.yakadexId)))
            return new EmptyTrainerResponse();
        var r = new EmptyTrainerResponse();
        r.status = "FAILURE";
        r.errorCode = "CREATION_FAILED";
        r.errorMessage = "Object creation failed";
        return r;
    }

    public EmptyTrainerResponse releaseYakamon(TrainerRequest trainer, YakamonRequest yakamon, ZoneRequest zone) {
        if (trainer == null || yakamon == null || zone == null) {
            var r = new EmptyTrainerResponse();
            r.status = "FAILURE";
            r.errorCode = "BAD_PARAMETER";
            r.errorMessage = "Bad parameter";
            return r;
        }
        Boolean result = trainerService.releaseYakamon(new TrainerEntity(trainer.uuid, trainer.name),
                new YakamonEntity(yakamon.uuid, yakamon.name, yakamon.yakadexId),
                new ZoneEntity(zone.name));
        if (result == null) {
            var r = new EmptyTrainerResponse();
            r.status = "FAILURE";
            r.errorCode = "CREATION_FAILED";
            r.errorMessage = "Object creation failed";
            return r;
        }
        else if (result)
            return new EmptyTrainerResponse();
        var r = new EmptyTrainerResponse();
        r.status = "FAILURE";
        r.errorCode = "UPDATE_FAILED";
        r.errorMessage = "Update failed";
        return r;
    }

    public TrainerRequest request(String arg) {
        try {
            return new TrainerRequest(UUID.fromString(arg));
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public TrainerRequest putRequest(String body) {
        ObjectMapper om = new ObjectMapper();
        var parser = new JSONParser();
        try {
            JSONObject obj = (JSONObject) parser.parse(body);
            if (obj.size() > 2)
                return new TrainerRequest(null, "BAD_BODY");
            return new TrainerRequest(UUID.fromString((String) obj.get("uuid")), (String) obj.get("name"));
        } catch (ParseException | IllegalArgumentException e) {
            return new TrainerRequest(null, "BAD_BODY");
        }
    }

    public TrainerRequest renameRequest(String uuid, String body) {
        ObjectMapper om = new ObjectMapper();
        var parser = new JSONParser();
        try {
            UUID uuidObj = UUID.fromString(uuid);
            JSONObject obj = (JSONObject) parser.parse(body);
            if (obj.size() > 1)
                return new TrainerRequest(null, "BAD_BODY");
            return new TrainerRequest(uuidObj, (String) obj.get("name"));
        } catch (ParseException e) {
            return new TrainerRequest(null, "BAD_BODY");
        } catch (IllegalArgumentException e) {
            return new TrainerRequest(null, "BAD_PARAMETER");
        }
    }

    public TrainerResponse updateFailedResponse() {
        var r = new TrainerResponse(null, null);
        r.status = "FAILURE";
        r.errorCode = "UPDATE_FAILED";
        r.errorMessage = "Update failed";
        return r;
    }

    public TrainerResponse badBodyResponse() {
        var r = new TrainerResponse(null, null);
        r.status = "FAILURE";
        r.errorCode = "BAD_BODY";
        r.errorMessage = "Bad body";
        return r;
    }

    public TrainerResponse objectCreationFailed() {
        var r = new TrainerResponse(null, null);
        r.status = "FAILURE";
        r.errorCode = "CREATION_FAILED";
        r.errorMessage = "Object creation failed";
        return r;
    }

    public ListUUIDTrainerControllerResponse badParameterUUIDResponse() {
        var r = new ListUUIDTrainerControllerResponse(null);
        r.status = "FAILURE";
        r.errorCode = "BAD_PARAMETER";
        r.errorMessage = "Bad parameter";
        return r;
    }

    public ListUUIDTrainerControllerResponse objectNotFoundUUIDResponse() {
        var r = new ListUUIDTrainerControllerResponse(null);
        r.status = "FAILURE";
        r.errorCode = "OBJECT_NOT_FOUND";
        r.errorMessage = "Object not found";
        return r;
    }

    public TrainerResponse badParameterResponse() {
        var r = new TrainerResponse(null, null);
        r.status = "FAILURE";
        r.errorCode = "BAD_PARAMETER";
        r.errorMessage = "Bad parameter";
        return r;
    }

    public TrainerResponse objectNotFoundResponse() {
        var r = new TrainerResponse(null, null);
        r.status = "FAILURE";
        r.errorCode = "OBJECT_NOT_FOUND";
        r.errorMessage = "Object not found";
        return r;
    }
}
