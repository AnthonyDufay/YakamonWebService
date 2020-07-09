package com.epita.assistants.yakamon.arch.service.yakamonservice;


import com.epita.assistants.yakamon.Yakamon;
import com.epita.assistants.yakamon.arch.Service;
import com.epita.assistants.yakamon.arch.repository.model.YakamonModel;
import com.epita.assistants.yakamon.arch.repository.model.ZoneModel;
import com.epita.assistants.yakamon.arch.repository.yakamonrepository.YakamonRepository;
import com.epita.assistants.yakamon.arch.service.entity.YakadexEntity;
import com.epita.assistants.yakamon.arch.service.entity.YakamonEntity;
import com.epita.assistants.yakamon.arch.service.entity.ZoneEntity;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class YakamonService {
    final private YakamonRepository yakamonRepository;
    final private Connection conn;

    public YakamonService(final Connection conn) {
        this.conn = conn;
        this.yakamonRepository = new YakamonRepository(conn);
    }

    public Connection getConn() {
        return conn;
    }

    public YakamonRepository getYakamonRepository() {
        return yakamonRepository;
    }

    public List<YakamonEntity> getAllYakamon() {
        final var result = yakamonRepository.getAllYakamon();

        ArrayList<YakamonEntity> list = new ArrayList<>();

        for (var model : result)
            list.add(new YakamonEntity(model.getUuid(), model.getName(), model.getYakadexId()));

        return list;
    }

    public YakamonEntity findYakamon(YakamonEntity req) {
        final var result = yakamonRepository.findYakamon(new YakamonModel(req.id, req.name, req.yakadexId));
        if (result == null)
            return null;
        return new YakamonEntity(result.getUuid(), result.getName(), result.getYakadexId());
    }

    public YakamonEntity putYakamon(YakamonEntity req, ZoneEntity zone) {
        final var result = yakamonRepository.putYakamon(new YakamonModel(req.id, req.name, req.yakadexId),
                new ZoneModel(zone.name));
        if (result == null)
            return null;
        return new YakamonEntity(result.getUuid(), result.getName(), result.getYakadexId());
    }

    public YakamonEntity changeYakamonName(YakamonEntity req) {
        final var result = yakamonRepository.changeYakamonName(new YakamonModel(req.id, req.name, req.yakadexId));
        if (result == null)
            return null;
        return new YakamonEntity(result.getUuid(), result.getName(), result.getYakadexId());
    }

    public YakamonEntity evolveYakamon(YakamonEntity req) {
        final var result = yakamonRepository.evolveYakamon(new YakamonModel(req.id, req.name, req.yakadexId));
        if (result == null)
            return null;
        return new YakamonEntity(result.getUuid(), result.getName(), result.getYakadexId());
    }

    public Boolean deleteYakamon(UUID req) {
        return yakamonRepository.deleteYakamon(req);
    }
}