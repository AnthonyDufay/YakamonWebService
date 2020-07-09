package com.epita.assistants.yakamon.arch.service.trainerservice;

import com.epita.assistants.yakamon.arch.Service;
import com.epita.assistants.yakamon.arch.repository.model.TrainerModel;
import com.epita.assistants.yakamon.arch.repository.model.YakamonModel;
import com.epita.assistants.yakamon.arch.repository.model.ZoneModel;
import com.epita.assistants.yakamon.arch.repository.trainerrepository.TrainerRepository;
import com.epita.assistants.yakamon.arch.service.entity.TrainerEntity;
import com.epita.assistants.yakamon.arch.service.entity.YakamonEntity;
import com.epita.assistants.yakamon.arch.service.entity.ZoneEntity;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TrainerService {
    final private TrainerRepository trainerRepository;
    final private Connection conn;

    public TrainerService(final Connection conn) {
        this.conn = conn;
        this.trainerRepository = new TrainerRepository(conn);
    }

    public Connection getConn() {
        return conn;
    }

    public TrainerRepository getTrainerRepository() {
        return trainerRepository;
    }

    public List<TrainerEntity> getAllTrainer() {
        final var result = trainerRepository.getAllTrainer();

        ArrayList<TrainerEntity> list = new ArrayList<>();

        for (var model : result)
            list.add(new TrainerEntity(model.getUuid(), model.getName()));

        return list;
    }

    public TrainerEntity findTrainer(TrainerEntity req) {
        final var result = trainerRepository.findTrainer(new TrainerModel(req.uuid, req.name));
        if (result == null)
            return null;
        return new TrainerEntity(result.getUuid(), result.getName());
    }

    public List<UUID> getYakamonsTrainer(TrainerEntity req) {
        return trainerRepository.getYakamonsTrainer(new TrainerModel(req.uuid, req.name));
    }

    public TrainerEntity createTrainer(TrainerEntity req) {
        final var result = trainerRepository.createTrainer(new TrainerModel(req.uuid, req.name));
        if (result == null)
            return null;
        return new TrainerEntity(result.getUuid(), result.getName());
    }

    public TrainerEntity renameTrainer(TrainerEntity req) {
        final var result = trainerRepository.renameTrainer(new TrainerModel(req.uuid, req.name));
        if (result == null)
            return null;
        return new TrainerEntity(result.getUuid(), result.getName());
    }

    public Boolean deleteTrainer(TrainerEntity req) {
        return trainerRepository.deleteTrainer(new TrainerModel(req.uuid, req.name));
    }

    public Boolean addYakamonToTrainer(TrainerEntity trainer, YakamonEntity yakamon) {
        return trainerRepository.addYakamonToTrainer(new TrainerModel(trainer.uuid, trainer.name),
                new YakamonModel(yakamon.id, yakamon.name, yakamon.yakadexId));
    }

    public Boolean releaseYakamon(TrainerEntity trainer, YakamonEntity yakamon, ZoneEntity zone) {
        return trainerRepository.releaseYakamon(new TrainerModel(trainer.uuid, trainer.name),
                new YakamonModel(yakamon.id, yakamon.name, yakamon.yakadexId),
                new ZoneModel(zone.name));
    }
}
