package com.epita.assistants.yakamon.arch.repository.trainerrepository;

import com.epita.assistants.ddl.tables.Yakamon;
import com.epita.assistants.yakamon.arch.repository.model.YakamonModel;
import com.epita.assistants.yakamon.arch.repository.model.ZoneModel;
import com.epita.assistants.yakamon.arch.repository.yakamonrepository.YakamonRepository;
import com.epita.assistants.yakamon.arch.repository.zonerepository.ZoneRepository;
import com.epita.assistants.ddl.tables.Trainer;
import com.epita.assistants.ddl.tables.TrainerYakamon;
import com.epita.assistants.yakamon.arch.Repository;
import com.epita.assistants.yakamon.arch.repository.model.TrainerModel;
import com.epita.assistants.ddl.tables.YakamonZone;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class TrainerRepository {
    final private Connection conn;

    public TrainerRepository(final Connection conn) {
        this.conn = conn;
    }

    public Connection getConn() {
        return conn;
    }

    public List<TrainerModel> getAllTrainer() {
        DSLContext ctx = DSL.using(conn, SQLDialect.POSTGRES);
        Result<Record> result = ctx.select().from(Trainer.TRAINER).fetch();

        ArrayList<TrainerModel> list = new ArrayList<>();

        for (var record : result)
            list.add(new TrainerModel(record.get(Trainer.TRAINER.UUID), record.get(Trainer.TRAINER.NAME)));

        return list;
    }

    public TrainerModel findTrainer(TrainerModel req) {
        for (var model : getAllTrainer()) {
            if (model.getUuid().equals(req.getUuid()))
                return model;
        }
        return null;
    }

    public List<UUID> getYakamonsTrainer(TrainerModel req) {
        if (findTrainer(req) == null)
            return null;
        DSLContext ctx = DSL.using(conn, SQLDialect.POSTGRES);
        Result<Record> result = ctx.select().from(TrainerYakamon.TRAINER_YAKAMON)
                .where(TrainerYakamon.TRAINER_YAKAMON.TRAINER_ID.eq(req.getUuid())).fetch();

        ArrayList<UUID> list = new ArrayList<>();

        for (var record : result)
            list.add(record.get(TrainerYakamon.TRAINER_YAKAMON.YAKAMON_ID));

        return list;
    }

    public TrainerModel createTrainer(TrainerModel req) {
        if (findTrainer(req) != null)
            return null;
        DSLContext ctx = DSL.using(conn, SQLDialect.POSTGRES);
        ctx.insertInto(Trainer.TRAINER, Trainer.TRAINER.UUID, Trainer.TRAINER.NAME)
                .values(req.getUuid(), req.getName()).execute();
        return req;
    }

    public TrainerModel renameTrainer(TrainerModel req) {
        if (findTrainer(req) == null)
            return null;
        DSLContext ctx = DSL.using(conn, SQLDialect.POSTGRES);
        ctx.update(Trainer.TRAINER).set(Trainer.TRAINER.NAME, req.getName())
                .where(Trainer.TRAINER.UUID.eq(req.getUuid())).execute();
        return findTrainer(req);
    }

    public Boolean deleteTrainer(TrainerModel req) {
        if (findTrainer(req) == null || !getYakamonsTrainer(req).isEmpty())
            return false;
        DSLContext ctx = DSL.using(conn, SQLDialect.POSTGRES);
        ctx.delete(Trainer.TRAINER).where(Trainer.TRAINER.UUID.eq(req.getUuid())).execute();
        return true;
    }

    public Boolean addYakamonToTrainer(TrainerModel trainer, YakamonModel yakamon) {
        var yakamonRepository = new YakamonRepository(conn);
        if (findTrainer(trainer) == null || yakamonRepository.findYakamon(yakamon) == null)
            return false;
        DSLContext ctx = DSL.using(conn, SQLDialect.POSTGRES);
        if (ctx.select().from(TrainerYakamon.TRAINER_YAKAMON)
                .where(TrainerYakamon.TRAINER_YAKAMON.YAKAMON_ID.eq(yakamon.getUuid())).fetch().isNotEmpty())
            return false;
        ctx.insertInto(TrainerYakamon.TRAINER_YAKAMON, TrainerYakamon.TRAINER_YAKAMON.YAKAMON_ID, TrainerYakamon.TRAINER_YAKAMON.TRAINER_ID)
                .values(yakamon.getUuid(), trainer.getUuid()).execute();
        ctx.delete(YakamonZone.YAKAMON_ZONE).where(YakamonZone.YAKAMON_ZONE.YAKAMON_ID.eq(yakamon.getUuid())).execute();
        return true;
    }

    public Boolean releaseYakamon(TrainerModel trainer, YakamonModel yakamon, ZoneModel zone) {
        var yakamonRepository = new YakamonRepository(conn);
        YakamonModel tmp = yakamonRepository.findYakamon(yakamon);
        if (!new ZoneRepository(conn).getPossibleYakamon(zone).contains(tmp.getYakadexId()))
            return null;
        var zoneRepository = new ZoneRepository(conn);
        if (findTrainer(trainer) == null || yakamonRepository.findYakamon(yakamon) == null
            || zoneRepository.findZone(zone) == null)
            return false;
        DSLContext ctx = DSL.using(conn, SQLDialect.POSTGRES);
        if (ctx.select().from(TrainerYakamon.TRAINER_YAKAMON)
            .where(TrainerYakamon.TRAINER_YAKAMON.YAKAMON_ID.eq(yakamon.getUuid())).fetch().isEmpty())
            return false;
        ctx.insertInto(YakamonZone.YAKAMON_ZONE, YakamonZone.YAKAMON_ZONE.YAKAMON_ID, YakamonZone.YAKAMON_ZONE.ZONE_ID)
                .values(yakamon.getUuid(), zone.getName()).execute();
        ctx.delete(TrainerYakamon.TRAINER_YAKAMON)
                .where(TrainerYakamon.TRAINER_YAKAMON.TRAINER_ID.eq(trainer.getUuid())
                        .and(TrainerYakamon.TRAINER_YAKAMON.YAKAMON_ID.eq(yakamon.getUuid()))).execute();
        return true;
    }
}
