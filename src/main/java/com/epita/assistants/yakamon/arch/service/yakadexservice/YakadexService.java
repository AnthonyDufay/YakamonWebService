package com.epita.assistants.yakamon.arch.service.yakadexservice;

import com.epita.assistants.yakamon.arch.Service;
import com.epita.assistants.yakamon.arch.controller.yakadexcontroller.YakadexRequest;
import com.epita.assistants.yakamon.arch.repository.model.YakadexModel;
import com.epita.assistants.yakamon.arch.repository.yakadexrepository.YakadexRepository;
import com.epita.assistants.yakamon.arch.service.entity.YakadexEntity;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@Service
public class YakadexService {
    final private YakadexRepository yakadexRepository;
    final private Connection conn;

    public YakadexService(final Connection conn) {
        this.conn = conn;
        this.yakadexRepository = new YakadexRepository(conn);
    }

    public YakadexRepository getYakadexRepository() {
        return yakadexRepository;
    }

    public Connection getConn() {
        return conn;
    }

    public List<YakadexEntity> getAllYakadexEntries() {
        final List<YakadexModel> result = yakadexRepository.getAllYakadexEntries();

        ArrayList<YakadexEntity> list = new ArrayList<>();

        for (var x : result)
            list.add(new YakadexEntity(x.getId(), x.getPreviousEvolutionYakadexId(), x.getNextEvolutionYakadexId(),
                    x.getMoveIds(), x.getMoveIds(), x.getTypeIds()));

        return list;
    }

    public YakadexEntity findYakadexEntrie(YakadexEntity req) {
        final YakadexModel result = yakadexRepository.findYakadexEntrie(new YakadexModel(req.id,
                null, null, null, null, null));
        if (result == null)
            return null;
        return new YakadexEntity(result.getId(), result.getPreviousEvolutionYakadexId(),
                result.getNextEvolutionYakadexId(), result.getMoveIds(), result.getTypeIds(), result.getZoneIds());
    }
}
