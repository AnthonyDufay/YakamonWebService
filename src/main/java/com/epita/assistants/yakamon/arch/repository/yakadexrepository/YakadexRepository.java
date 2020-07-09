package com.epita.assistants.yakamon.arch.repository.yakadexrepository;

import com.epita.assistants.ddl.tables.*;
import com.epita.assistants.yakamon.arch.Repository;
import com.epita.assistants.yakamon.arch.repository.model.YakadexModel;
import org.jooq.*;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@Repository
public class YakadexRepository {
    final private Connection conn;

    public YakadexRepository(final Connection conn) {
        this.conn = conn;
    }

    public Connection getConn() {
        return conn;
    }

    public List<YakadexModel> getAllYakadexEntries() {
        DSLContext ctx = DSL.using(conn, SQLDialect.POSTGRES);
        Result<Record> result = ctx.select().from(Yakadex.YAKADEX).fetch();

        ArrayList<YakadexModel> list = new ArrayList<>();

        for (var x : result) {
            Result<Record1<String>> moves = ctx.select(YakadexMoves.YAKADEX_MOVES.MOVE_ID).from(YakadexMoves.YAKADEX_MOVES)
                    .where(YakadexMoves.YAKADEX_MOVES.YAKADEX_ID.eq(x.get(Yakadex.YAKADEX.NAME))).fetch();

            Result<Record1<String>> types = ctx.select(YakadexTypes.YAKADEX_TYPES.TYPE_ID).from(YakadexTypes.YAKADEX_TYPES)
                    .where(YakadexTypes.YAKADEX_TYPES.YAKADEX_ID.eq(x.get(Yakadex.YAKADEX.NAME))).fetch();

            Result<Record1<String>> zones = ctx.select(YakadexZones.YAKADEX_ZONES.ZONE_ID).from(YakadexZones.YAKADEX_ZONES)
                    .where(YakadexZones.YAKADEX_ZONES.YAKADEX_ID.eq(x.get(Yakadex.YAKADEX.NAME))).fetch();

            ArrayList<String> moveList = new ArrayList<>();
            ArrayList<String> typeList = new ArrayList<>();
            ArrayList<String> zoneList = new ArrayList<>();

            for (var move : moves)
                moveList.add(move.value1());
            for (var type : types)
                typeList.add(type.value1());
            for (var zone : zones)
                zoneList.add(zone.value1());

            list.add(new YakadexModel(x.get(Yakadex.YAKADEX.NAME), x.get(Yakadex.YAKADEX.PREVIOUS_EVOLUTION),
                    x.get(Yakadex.YAKADEX.NEXT_EVOLUTION), moveList, typeList, zoneList));
        }
        return list;
    }

    public YakadexModel findYakadexEntrie(YakadexModel yaka) {
        for (var x : getAllYakadexEntries()) {
            if (x.getId().equals(yaka.getId()))
                return x;
        }
        return null;
    }
}
