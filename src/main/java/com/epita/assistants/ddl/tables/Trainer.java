/*
 * This file is generated by jOOQ.
 */
package com.epita.assistants.ddl.tables;


import com.epita.assistants.ddl.Indexes;
import com.epita.assistants.ddl.Keys;
import com.epita.assistants.ddl.Webservice;
import com.epita.assistants.ddl.tables.records.TrainerRecord;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.annotation.processing.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row2;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.3"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Trainer extends TableImpl<TrainerRecord> {

    private static final long serialVersionUID = -1450320369;

    /**
     * The reference instance of <code>WEBSERVICE.TRAINER</code>
     */
    public static final Trainer TRAINER = new Trainer();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TrainerRecord> getRecordType() {
        return TrainerRecord.class;
    }

    /**
     * The column <code>WEBSERVICE.TRAINER.UUID</code>.
     */
    public final TableField<TrainerRecord, UUID> UUID = createField(DSL.name("UUID"), org.jooq.impl.SQLDataType.UUID.nullable(false), this, "");

    /**
     * The column <code>WEBSERVICE.TRAINER.NAME</code>.
     */
    public final TableField<TrainerRecord, String> NAME = createField(DSL.name("NAME"), org.jooq.impl.SQLDataType.VARCHAR(128).nullable(false), this, "");

    /**
     * Create a <code>WEBSERVICE.TRAINER</code> table reference
     */
    public Trainer() {
        this(DSL.name("TRAINER"), null);
    }

    /**
     * Create an aliased <code>WEBSERVICE.TRAINER</code> table reference
     */
    public Trainer(String alias) {
        this(DSL.name(alias), TRAINER);
    }

    /**
     * Create an aliased <code>WEBSERVICE.TRAINER</code> table reference
     */
    public Trainer(Name alias) {
        this(alias, TRAINER);
    }

    private Trainer(Name alias, Table<TrainerRecord> aliased) {
        this(alias, aliased, null);
    }

    private Trainer(Name alias, Table<TrainerRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> Trainer(Table<O> child, ForeignKey<O, TrainerRecord> key) {
        super(child, key, TRAINER);
    }

    @Override
    public Schema getSchema() {
        return Webservice.WEBSERVICE;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.PRIMARY_KEY_E);
    }

    @Override
    public UniqueKey<TrainerRecord> getPrimaryKey() {
        return Keys.CONSTRAINT_E;
    }

    @Override
    public List<UniqueKey<TrainerRecord>> getKeys() {
        return Arrays.<UniqueKey<TrainerRecord>>asList(Keys.CONSTRAINT_E);
    }

    @Override
    public Trainer as(String alias) {
        return new Trainer(DSL.name(alias), this);
    }

    @Override
    public Trainer as(Name alias) {
        return new Trainer(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Trainer rename(String name) {
        return new Trainer(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Trainer rename(Name name) {
        return new Trainer(name, null);
    }

    // -------------------------------------------------------------------------
    // Row2 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row2<UUID, String> fieldsRow() {
        return (Row2) super.fieldsRow();
    }
}
