package com.epita.assistants.yakamon.arch.service.moveservice;

import com.epita.assistants.yakamon.arch.Service;
import com.epita.assistants.yakamon.arch.controller.movecontroller.MoveRequest;
import com.epita.assistants.yakamon.arch.repository.model.MoveModel;
import com.epita.assistants.yakamon.arch.repository.moverepository.MoveRepository;
import com.epita.assistants.yakamon.arch.service.entity.MoveEntity;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@Service
public class MoveService {
    final private MoveRepository moveRepository;
    final private Connection conn;

    public MoveService(final Connection conn) {
        this.conn = conn;
        moveRepository = new MoveRepository(conn);
    }

    public List<MoveEntity> getAllMove() {
        final List<MoveModel> result = moveRepository.getAllMove();

        ArrayList<MoveEntity> list = new ArrayList<>();

        for (var x : result)
            list.add(new MoveEntity(x.getName()));

        return list;
    }

    public MoveEntity findMove(MoveEntity move) {
        final MoveModel result = moveRepository.findMove(new MoveModel(move.name));
        if (result == null)
            return null;
        return new MoveEntity(result.getName());
    }

    public Connection getConn() {
        return conn;
    }
}
