package com.epita.assistants.yakamon;

import com.epita.assistants.yakamon.arch.controller.movecontroller.MoveController;
import com.epita.assistants.yakamon.arch.controller.movecontroller.MoveRequest;
import com.epita.assistants.yakamon.arch.controller.statscontroller.StatsController;
import com.epita.assistants.yakamon.arch.controller.trainercontroller.TrainerController;
import com.epita.assistants.yakamon.arch.controller.typecontroller.TypeController;
import com.epita.assistants.yakamon.arch.controller.typecontroller.TypeRequest;
import com.epita.assistants.yakamon.arch.controller.yakadexcontroller.YakadexController;
import com.epita.assistants.yakamon.arch.controller.yakadexcontroller.YakadexRequest;
import com.epita.assistants.yakamon.arch.controller.yakamoncontroller.YakamonController;
import com.epita.assistants.yakamon.arch.controller.zonecontroller.ZoneController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.h2.jdbcx.JdbcDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import spark.Spark;

/**
 * Entry point for Yakamon.
 *
 */
public final class Yakamon {

    /**
     * Setup database connection
     *
     * @return Optional with the Connection if succeed
     */
    private static Optional<Connection> setupConnection() {
        final JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:./webservice");
        dataSource.setUser("sa");
        dataSource.setPassword("");
        try {
            return Optional.of(dataSource.getConnection());
        } catch (SQLException sqlException) {
            return Optional.empty();
        }
    }

    public static void start(final int port) {
        // 1 - Instantiate your Controllers
        // 2 - Register your endpoints with the good address and the good
        // controller method
        // 3 - Set the port of spark
        // Be careful: remember that you must set the content type
        var optConn = setupConnection();
        Connection conn = null;
        if (optConn.isPresent())
            conn = optConn.get();
        TypeController typeController = new TypeController(conn);
        MoveController moveController = new MoveController(conn);
        YakadexController yakadexController = new YakadexController(conn);
        YakamonController yakamonController = new YakamonController(conn);
        ZoneController zoneController = new ZoneController(conn);
        TrainerController trainerController = new TrainerController(conn);
        StatsController statsController = new StatsController(conn);
        ObjectWriter ow = new ObjectMapper().writer();
        Spark.port(port);
        Spark.get("/type/", (request, response) -> {
            response.type("application/json");
            return ow.writeValueAsString(typeController.getAllType()); });
        Spark.get("type/:name", ((request, response) -> {
            response.type("application/json");
            return ow.writeValueAsString(typeController.findType(typeController.request(request.params(":name"))));
        }));
        Spark.get("/move/", (request, response) -> {
            response.type("application/json");
            return ow.writeValueAsString(moveController.getAllMove());
        });
        Spark.get("/move/:name", (request, response) -> {
            response.type("application/json");
            return ow.writeValueAsString(moveController.findMove(moveController.request(request.params(":name"))));
        });
        Spark.get("/yakadex/", (request, response) -> {
            response.type("application/json");
            return ow.writeValueAsString(yakadexController.getAllYakadexEntries());
        });
        Spark.get("/yakadex/:id", (request, response) -> {
            response.type("application/json");
            return ow.writeValueAsString(yakadexController.findYakadexEntrie(yakadexController.request(request.params(":id"))));
        });
        Spark.get("/zone/", (request, response) -> {
            response.type("application/json");
            return ow.writeValueAsString(zoneController.getAllZones());
        });
        Spark.get("/zone/:name", (request, response) -> {
            response.type("application/json");
            return ow.writeValueAsString(zoneController.findZone(zoneController.request(request.params(":name"))));
        });
        Spark.get("/yakamon/", (request, response) -> {
            response.type("application/json");
            return ow.writeValueAsString(yakamonController.getAllYakamon());
        });
        Spark.get("/yakamon/:id", (request, response) -> {
            response.type("application/json");
            return ow.writeValueAsString(yakamonController.findYakamon(yakamonController.request(request.params(":id"))));
        });
        Spark.get("/zone/:name/currentYakamons", (request, response) -> {
            response.type("application/json");
            return ow.writeValueAsString(zoneController.getCurrentYakamon(zoneController.request(request.params(":name"))));
        });
        Spark.get("/zone/:name/possibleYakamons", (request, response) -> {
            response.type("application/json");
            return ow.writeValueAsString(zoneController.getPossibleYakamon(zoneController.request(request.params(":name"))));
        });
        Spark.put("/yakamon/", (request, response) -> {
            response.type("application/json");
            return ow.writeValueAsString(yakamonController.putYakamon(yakamonController.putRequest(request.body())));
        });
        Spark.patch("/yakamon/:id/rename", (request, response) -> {
            response.type("application/json");
            return ow.writeValueAsString(yakamonController.changeYakamonName(yakamonController
                    .patchRenameRequest(request.body(), request.params(":id"))));
        });
        Spark.patch("/yakamon/:id/evolve", (request, response) -> {
            response.type("application/json");
            return ow.writeValueAsString(yakamonController.evolveYakamon(yakamonController.request(request.params(":id"))));
        });
        Spark.delete("/yakamon/:id", (request, response) -> { // TODO create a empty response
            response.type("application/json");
            return ow.writeValueAsString(yakamonController.deleteYakamon(yakamonController.deletionRequest(request.params(":id"))));
        });
        Spark.get("/trainer/", (request, response) -> {
            response.type("application/json");
            return ow.writeValueAsString(trainerController.getAllTrainer());
        });
        Spark.get("/trainer/:uuid", (request, response) -> {
            response.type("application/json");
            return ow.writeValueAsString(trainerController.findTrainer(trainerController.request(request.params(":uuid"))));
        });
        Spark.get("/trainer/:uuid/yakamons", (request, response) -> {
            response.type("application/json");
            return ow.writeValueAsString(trainerController.getYakamonsTrainer(trainerController.request(request.params(":uuid"))));
        });
        Spark.put("/trainer/", (request, response) -> {
            response.type("application/json");
            return ow.writeValueAsString(trainerController.createTrainer(trainerController.putRequest(request.body())));
        });
        Spark.patch("/trainer/:uuid", (request, response) -> {
            response.type("application/json");
            return ow.writeValueAsString(trainerController.renameTrainer(trainerController
                    .renameRequest(request.params(":uuid"), request.body())));
        });
        Spark.delete("/trainer/:uuid", (request, response) -> {
            response.type("application/json");
            return ow.writeValueAsString(trainerController.deleteTrainer(trainerController.request(request.params(":uuid"))));
        });
        Spark.post("/trainer/:uuid/yakamons/:yakamon", (request, response) -> {
            response.type("application/json");
            return ow.writeValueAsString(trainerController.addYakamonToTrainer(trainerController.request(request.params(":uuid")),
                    yakamonController.request(request.params(":yakamon"))));
        });
        Spark.post("/trainer/:uuid/free/:yakamon/:zoneName", (request, response) -> {
            response.type("application/json");
            return ow.writeValueAsString(trainerController.releaseYakamon(trainerController.request(request.params(":uuid")),
                    yakamonController.request(request.params(":yakamon")),
                    zoneController.request(request.params(":zoneName"))));
        });
        Spark.get("stats/zone/distribution", (request, response) -> {
            response.type("application/json");
            return ow.writeValueAsString(statsController.getAllDistribution());
        });
        Spark.get("stats/zone/distribution/:zoneId", (request, response) -> {
            response.type("application/json");
            return ow.writeValueAsString(statsController.getZoneDistribution(zoneController.request(request.params(":zoneId"))));
        });
        Spark.get("stats/yakamon/top/:topNb", (request, response) -> {
            response.type("application/json");
            return ow.writeValueAsString(statsController.getTopNb(statsController.topNbRequest(request.params(":topNb"))));
        });
    }

    /**
     * Main method.
     *
     * @param args Command line arguments.
     */
    public static void main(final String... args) {
        start(4567);
    }
}
