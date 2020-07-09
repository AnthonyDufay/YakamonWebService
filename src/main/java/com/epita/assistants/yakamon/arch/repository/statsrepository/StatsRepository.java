package com.epita.assistants.yakamon.arch.repository.statsrepository;

import com.epita.assistants.ddl.tables.*;
import com.epita.assistants.yakamon.arch.Repository;
import com.epita.assistants.yakamon.arch.repository.model.ZoneModel;
import com.epita.assistants.yakamon.arch.repository.zonerepository.ZoneRepository;
import com.epita.assistants.yakamon.misc.Pair;
import org.jooq.*;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class StatsRepository {
    final private Connection conn;

    public StatsRepository(final Connection conn) {
        this.conn = conn;
    }

    public Connection getConn() {
        return conn;
    }

    public Map<String, Map<String, Float>> getAllDistribution() {
        DSLContext ctx = DSL.using(conn, SQLDialect.POSTGRES);



        Result<Record1<String>> zoneIds = ctx.select(Zone.ZONE.NAME).from(Zone.ZONE).fetch();

        int nbYaka = ctx.select().from(YakamonZone.YAKAMON_ZONE).fetch().size();

        HashMap<String, Map<String, Float>> map = new HashMap<>();

        for (var zone : zoneIds) {
            HashMap<String, Float> distribution = new HashMap<>();
            var yakadexIds = new ZoneRepository(conn).getPossibleYakamon(new ZoneModel(zone.value1()));
            for (var yakadexId : yakadexIds) {
                var yakamonsInZone = new ZoneRepository(conn).getCurrentYakamon(new ZoneModel(zone.value1()));
                int nbYakaInZoneWithGoodId = 0;
                for (var yakamon : yakamonsInZone) {
                    String name = ctx.select(Yakamon.YAKAMON.YAKADEX_ID).from(Yakamon.YAKAMON)
                            .where(Yakamon.YAKAMON.ID.eq(yakamon))
                            .fetch().get(0).value1();
                    if (name.equals(yakadexId))
                        nbYakaInZoneWithGoodId += 1;
                }
                float result = (float) 0;
                if (nbYaka != 0)
                    result = (float) nbYakaInZoneWithGoodId / (float) nbYaka;
                distribution.put(yakadexId, result);
            }
            map.put(zone.value1(), distribution);
        }
        return map;
    }

    public Map<String, Float> getZoneDistribution(ZoneModel zone) {
        ZoneRepository zoneRepository = new ZoneRepository(conn);
        if (zoneRepository.findZone(zone) == null)
            return null;
        DSLContext ctx = DSL.using(conn, SQLDialect.POSTGRES);
        HashMap<String, Float> distribution = new HashMap<>();
        var yakadexIds = new ZoneRepository(conn).getPossibleYakamon(new ZoneModel(zone.getName()));
        for (var yakadexId : yakadexIds) {
            var yakamonsInZone = new ZoneRepository(conn).getCurrentYakamon(new ZoneModel(zone.getName()));
            int nbYakaInZoneWithGoodId = 0;
            int nbYaka = yakamonsInZone.size();
            for (var yakamon : yakamonsInZone) {
                String name = ctx.select(Yakamon.YAKAMON.YAKADEX_ID).from(Yakamon.YAKAMON)
                        .where(Yakamon.YAKAMON.ID.eq(yakamon))
                        .fetch().get(0).value1();
                if (name.equals(yakadexId))
                    nbYakaInZoneWithGoodId += 1;
            }
            float result = (float) 0;
            if (nbYaka != 0)
                result = (float) nbYakaInZoneWithGoodId / (float) nbYaka;
            distribution.put(yakadexId, result);
        }
        return distribution;
    }

    public List<String> getTopNb(Integer topNb) {
        if (topNb <= 0)
            return null;
        DSLContext ctx = DSL.using(conn, SQLDialect.POSTGRES);

        Result<Record1<String>> yakamonsTrainer = ctx.select(Yakamon.YAKAMON.YAKADEX_ID).from(Yakamon.YAKAMON)
                .join(TrainerYakamon.TRAINER_YAKAMON)
                .on(Yakamon.YAKAMON.ID.eq(TrainerYakamon.TRAINER_YAKAMON.YAKAMON_ID)).fetch();

        Result<Record1<String>> yakadexs = ctx.select(Yakadex.YAKADEX.NAME).from(Yakadex.YAKADEX).fetch();

        ArrayList<Pair> list = new ArrayList<>();

        for (var x : yakadexs) {
            list.add(new Pair(x.value1(), 0));
        }

        for (var record : yakamonsTrainer) {
            String tmp = record.value1();
            for (var x : list) {
                if (x.id.equals(tmp)) {
                    x.nb++;
                    break;
                }
            }
        }

        ArrayList<String> sortedList = new ArrayList<>();
        while (!list.isEmpty()) {
            Pair max = list.get(0);
            for (var x : list) {
                if (x.nb > max.nb)
                    max = x;
                else if (x.nb.equals(max.nb)) {
                    if (x.id.compareTo(max.id) < 0)
                        max = x;
                }
            }
            sortedList.add(max.id);
            list.remove(max);
        }
        return sortedList.stream().limit(topNb).collect(Collectors.toList());
    }
}
