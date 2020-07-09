package com.epita.assistants.yakamon.arch.repository.moverepository;

import com.epita.assistants.ddl.tables.Move;
import com.epita.assistants.yakamon.arch.Repository;
import com.epita.assistants.yakamon.arch.repository.model.MoveModel;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MoveRepository {

    private final Connection conn;

    public MoveRepository(final Connection conn) {
        this.conn = conn;
    }

    public List<MoveModel> getAllMove() {
        String url = "jdbc:postgresql:jOOQ";

        DSLContext ctx = DSL.using(conn, SQLDialect.POSTGRES);
        Result<Record> result = ctx.select().from(Move.MOVE).fetch();

        ArrayList<MoveModel> list = new ArrayList<>();

        for (Record r : result)
            list.add(new MoveModel(r.get(Move.MOVE.NAME)));

        return list;
    }

    public MoveModel findMove(MoveModel move) {
        for (var x : getAllMove()) {
            if (x.getName().equals(move.getName()))
                return x;
        }
        return null;
    }
}
