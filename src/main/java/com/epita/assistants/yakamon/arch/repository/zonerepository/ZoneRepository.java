package com.epita.assistants.yakamon.arch.repository.zonerepository;

import com.epita.assistants.ddl.tables.*;
import com.epita.assistants.yakamon.arch.Repository;
import com.epita.assistants.yakamon.arch.repository.model.ZoneModel;
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
public class ZoneRepository {
    private final Connection conn;

    public ZoneRepository(final Connection conn) {
        this.conn = conn;
    }

    public Connection getConn() {
        return conn;
    }

    public List<ZoneModel> getAllZones() {
        DSLContext ctx = DSL.using(conn, SQLDialect.POSTGRES);
        Result<Record> result = ctx.select().from(Zone.ZONE).fetch();

        ArrayList<ZoneModel> list = new ArrayList<>();

        for (var record : result)
            list.add(new ZoneModel(record.get(Zone.ZONE.NAME)));

        return list;
    }

    public ZoneModel findZone(ZoneModel req) {
        for (var zone : getAllZones()) {
            if (zone.getName().equals(req.getName()))
                return zone;
        }
        return null;
    }

    public List<UUID> getCurrentYakamon(ZoneModel req) {
        if (findZone(req) == null)
            return null;
        DSLContext ctx = DSL.using(conn, SQLDialect.POSTGRES);
        Result<Record> result = ctx.select().from(Yakamon.YAKAMON).join(YakamonZone.YAKAMON_ZONE)
                .on(Yakamon.YAKAMON.ID.eq(YakamonZone.YAKAMON_ZONE.YAKAMON_ID))
                .where(YakamonZone.YAKAMON_ZONE.ZONE_ID.eq(req.getName())).fetch();

        ArrayList<UUID> list = new ArrayList<>();

        for (var record : result)
            list.add(record.get(Yakamon.YAKAMON.ID));

        return list;
    }

    public List<String> getPossibleYakamon(ZoneModel req) {
        if (findZone(req) == null)
            return null;
        DSLContext ctx = DSL.using(conn, SQLDialect.POSTGRES);
        Result<Record> result = ctx.select().from(Yakadex.YAKADEX).join(YakadexZones.YAKADEX_ZONES)
                .on(Yakadex.YAKADEX.NAME.eq(YakadexZones.YAKADEX_ZONES.YAKADEX_ID))
                .where(YakadexZones.YAKADEX_ZONES.ZONE_ID.eq(req.getName())).fetch();

        ArrayList<String> list = new ArrayList<>();

        for (var record : result)
            list.add(record.get(Yakadex.YAKADEX.NAME));

        return list;
    }
}
