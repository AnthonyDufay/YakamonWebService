package com.epita.assistants.yakamon.arch.repository.yakamonrepository;

import com.epita.assistants.ddl.tables.*;
import com.epita.assistants.yakamon.arch.Repository;
import com.epita.assistants.yakamon.arch.repository.model.YakadexModel;
import com.epita.assistants.yakamon.arch.repository.model.YakamonModel;
import com.epita.assistants.yakamon.arch.repository.model.ZoneModel;
import com.epita.assistants.yakamon.arch.repository.yakadexrepository.YakadexRepository;
import com.epita.assistants.yakamon.arch.repository.zonerepository.ZoneRepository;
import org.jooq.*;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class YakamonRepository {
    final private Connection conn;

    public YakamonRepository(final Connection conn) {
        this.conn = conn;
    }

    public Connection getConn() {
        return conn;
    }

    public List<YakamonModel> getAllYakamon() {
        DSLContext ctx = DSL.using(conn, SQLDialect.POSTGRES);
        Result<Record> result = ctx.select().from(Yakamon.YAKAMON).fetch();

        ArrayList<YakamonModel> list = new ArrayList<>();

        for (var record : result)
            list.add(new YakamonModel(record.get(Yakamon.YAKAMON.ID), record.get(Yakamon.YAKAMON.NAME),
                    record.get(Yakamon.YAKAMON.YAKADEX_ID)));

        return list;
    }

    public YakamonModel findYakamon(YakamonModel req) {
        for (var yakamon : getAllYakamon()) {
            if (yakamon.getUuid().equals(req.getUuid()))
                return yakamon;
        }
        return null;
    }

    public YakamonModel putYakamon(YakamonModel req, ZoneModel zone) {
        if (findYakamon(req) != null)
            return null;
        var zoneRepo = new ZoneRepository(conn);
        if (zoneRepo.findZone(zone) == null)
            return null;
        if (!zoneRepo.getPossibleYakamon(zone).contains(req.getYakadexId()))
            return null;
        var yakadex = new YakadexModel(req.getYakadexId(), null, null,
                null, null, null);
        var yakadexRepo = new YakadexRepository(conn);
        if (yakadexRepo.findYakadexEntrie(yakadex) == null)
            return null;
        DSLContext ctx = DSL.using(conn, SQLDialect.POSTGRES);
        ctx.insertInto(Yakamon.YAKAMON, Yakamon.YAKAMON.ID, Yakamon.YAKAMON.NAME, Yakamon.YAKAMON.YAKADEX_ID)
                .values(req.getUuid(), req.getName(), req.getYakadexId()).execute();
        ctx.insertInto(YakamonZone.YAKAMON_ZONE, YakamonZone.YAKAMON_ZONE.YAKAMON_ID, YakamonZone.YAKAMON_ZONE.ZONE_ID)
                .values(req.getUuid(), zone.getName()).execute();
        return req;
    }

    public YakamonModel changeYakamonName(YakamonModel req) {
        if (findYakamon(req) == null)
            return null;
        DSLContext ctx = DSL.using(conn, SQLDialect.POSTGRES);
        ctx.update(Yakamon.YAKAMON).set(Yakamon.YAKAMON.NAME, req.getName())
                .where(Yakamon.YAKAMON.ID.eq(req.getUuid())).execute();
        return findYakamon(req);
    }

    public YakamonModel evolveYakamon(YakamonModel req) {
        YakamonModel yakamon = findYakamon(req);
        if (yakamon == null)
            return null;
        DSLContext ctx = DSL.using(conn, SQLDialect.POSTGRES);
        Result<Record1<String>> evolution = ctx.select(Yakadex.YAKADEX.NEXT_EVOLUTION).from(Yakadex.YAKADEX)
                .where(Yakadex.YAKADEX.NAME.eq(yakamon.getYakadexId())).fetch();
        String evolutionString = evolution.get(0).value1();
        Result<Record1<String>> currentYakadexIdRecord = ctx.select(Yakamon.YAKAMON.YAKADEX_ID).from(Yakamon.YAKAMON)
                .where(Yakamon.YAKAMON.ID.eq(req.getUuid())).fetch();
        Result<Record1<String>> eventualZone = ctx.select(YakamonZone.YAKAMON_ZONE.ZONE_ID).from(YakamonZone.YAKAMON_ZONE)
                .where(YakamonZone.YAKAMON_ZONE.YAKAMON_ID.eq(req.getUuid())).fetch();
        if (eventualZone.isNotEmpty()) {
            String zone = eventualZone.get(0).value1();
            ZoneRepository zoneRepository = new ZoneRepository(conn);
            if (!zoneRepository.getPossibleYakamon(new ZoneModel(zone)).contains(evolutionString))
                return null;
        }
        if (evolutionString == null)
            return new YakamonModel(null, "UPDATE_FAILED", null);

        ctx.update(Yakamon.YAKAMON).set(Yakamon.YAKAMON.YAKADEX_ID, evolutionString)
                .where(Yakamon.YAKAMON.ID.eq(req.getUuid())).execute();
        return findYakamon(req);
    }

    public Boolean deleteYakamon(UUID req) {
        if (findYakamon(new YakamonModel(req, null, null)) == null)
            return false;
        DSLContext ctx = DSL.using(conn, SQLDialect.POSTGRES);
        ctx.delete(YakamonZone.YAKAMON_ZONE).where(YakamonZone.YAKAMON_ZONE.YAKAMON_ID.eq(req)).execute();
        ctx.delete(TrainerYakamon.TRAINER_YAKAMON).where(TrainerYakamon.TRAINER_YAKAMON.YAKAMON_ID.eq(req)).execute();
        ctx.delete(Yakamon.YAKAMON).where(Yakamon.YAKAMON.ID.eq(req)).execute();
        return true;
    }
}
