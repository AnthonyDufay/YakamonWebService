package com.epita.assistants.yakamon.arch.repository.typerepository;

import com.epita.assistants.ddl.tables.Type;
import com.epita.assistants.yakamon.arch.Repository;
import com.epita.assistants.yakamon.arch.repository.model.TypeModel;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TypeRepository {

    private final Connection conn;

    public TypeRepository(final Connection conn) {
        this.conn = conn;
    }

    public List<TypeModel> getAllType() {
        DSLContext ctx = DSL.using(conn, SQLDialect.POSTGRES);
        Result<Record> result = ctx.select().from(Type.TYPE).fetch();

        ArrayList<TypeModel> list = new ArrayList<>();

        for (Record r : result)
            list.add(new TypeModel(r.get(Type.TYPE.NAME)));

        return list;
    }

    public TypeModel findType(TypeModel type) {
        for (var x : getAllType()) {
            if (x.getType().equals(type.getType()))
                return x;
        }
        return null;
    }

    public Connection getConn() {
        return conn;
    }
}
