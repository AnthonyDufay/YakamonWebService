package com.epita.assistants.yakamon.arch.service.typeservice;

import com.epita.assistants.yakamon.arch.Service;
import com.epita.assistants.yakamon.arch.controller.typecontroller.TypeRequest;
import com.epita.assistants.yakamon.arch.repository.model.TypeModel;
import com.epita.assistants.yakamon.arch.repository.typerepository.TypeRepository;
import com.epita.assistants.yakamon.arch.service.entity.TypeEntity;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@Service
public class TypeService {

    final private TypeRepository typeRepository;
    final private Connection conn;

    public TypeService(final Connection conn) {
        this.conn = conn;
        this.typeRepository = new TypeRepository(conn);
    }

    public List<TypeEntity> getAllType() {
        final List<TypeModel> types = typeRepository.getAllType();

        ArrayList<TypeEntity> list = new ArrayList<>();

        for (var x : types)
            list.add(new TypeEntity(x.getType()));

        return list;
    }

    public TypeEntity findType(TypeEntity name) {
        final TypeModel type = typeRepository.findType(new TypeModel(name.name));
        if (type == null)
            return null;
        return new TypeEntity(type.getType());
    }

    public Connection getConn() {
        return conn;
    }
}
