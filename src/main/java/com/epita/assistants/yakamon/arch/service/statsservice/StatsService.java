package com.epita.assistants.yakamon.arch.service.statsservice;

import com.epita.assistants.yakamon.arch.Service;
import com.epita.assistants.yakamon.arch.repository.model.ZoneModel;
import com.epita.assistants.yakamon.arch.repository.statsrepository.StatsRepository;
import com.epita.assistants.yakamon.arch.service.entity.ZoneEntity;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

@Service
public class StatsService {
    final private StatsRepository statsRepository;
    final private Connection conn;

    public StatsService(final Connection conn) {
        this.conn = conn;
        this.statsRepository = new StatsRepository(conn);
    }


    public Connection getConn() {
        return conn;
    }

    public StatsRepository getStatsRepository() {
        return statsRepository;
    }

    public Map<String, Map<String, Float>> getAllDistribution() {
        return statsRepository.getAllDistribution();
    }

    public Map<String, Float> getZoneDistribution(ZoneEntity zone) {
        return statsRepository.getZoneDistribution(new ZoneModel(zone.name));
    }

    public List<String> getTopNb(Integer topNb) {
        return statsRepository.getTopNb(topNb);
    }
}
