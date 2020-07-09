package com.epita.assistants.yakamon.arch.service.zoneservice;

import com.epita.assistants.yakamon.arch.Service;
import com.epita.assistants.yakamon.arch.repository.model.ZoneModel;
import com.epita.assistants.yakamon.arch.repository.zonerepository.ZoneRepository;
import com.epita.assistants.yakamon.arch.service.entity.YakamonEntity;
import com.epita.assistants.yakamon.arch.service.entity.ZoneEntity;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ZoneService {
    final private ZoneRepository zoneRepository;
    final private Connection conn;

    public ZoneService(final Connection conn) {
        this.conn = conn;
        this.zoneRepository = new ZoneRepository(conn);
    }

    public Connection getConn() {
        return conn;
    }

    public ZoneRepository getZoneRepository() {
        return zoneRepository;
    }

    public List<ZoneEntity> getAllZones() {
        final List<ZoneModel> result = zoneRepository.getAllZones();

        ArrayList<ZoneEntity> list = new ArrayList<>();

        for (var model : result)
            list.add(new ZoneEntity(model.getName()));

        return list;
    }

    public ZoneEntity findZone(ZoneEntity req) {
        final ZoneModel result = zoneRepository.findZone(new ZoneModel(req.name));
        if (result == null)
            return null;
        return new ZoneEntity(result.getName());
    }

    public List<UUID> getCurrentYakamon(ZoneEntity req) {
        return zoneRepository.getCurrentYakamon(new ZoneModel(req.name));
    }

    public List<String> getPossibleYakamon(ZoneEntity req) {
        return zoneRepository.getPossibleYakamon(new ZoneModel(req.name));
    }
}
