/*
 * This file is generated by jOOQ.
 */
package com.epita.assistants.ddl.tables;


import com.epita.assistants.ddl.Indexes;
import com.epita.assistants.ddl.Keys;
import com.epita.assistants.ddl.Webservice;
import com.epita.assistants.ddl.tables.records.YakadexMovesRecord;

import java.util.Arrays;
import java.util.List;

import javax.annotation.processing.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row3;
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
public class YakadexMoves extends TableImpl<YakadexMovesRecord> {

    private static final long serialVersionUID = 319475166;

    /**
     * The reference instance of <code>WEBSERVICE.YAKADEX_MOVES</code>
     */
    public static final YakadexMoves YAKADEX_MOVES = new YakadexMoves();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<YakadexMovesRecord> getRecordType() {
        return YakadexMovesRecord.class;
    }

    /**
     * The column <code>WEBSERVICE.YAKADEX_MOVES.ID</code>.
     */
    public final TableField<YakadexMovesRecord, Integer> ID = createField(DSL.name("ID"), org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>WEBSERVICE.YAKADEX_MOVES.YAKADEX_ID</code>.
     */
    public final TableField<YakadexMovesRecord, String> YAKADEX_ID = createField(DSL.name("YAKADEX_ID"), org.jooq.impl.SQLDataType.VARCHAR(128).nullable(false), this, "");

    /**
     * The column <code>WEBSERVICE.YAKADEX_MOVES.MOVE_ID</code>.
     */
    public final TableField<YakadexMovesRecord, String> MOVE_ID = createField(DSL.name("MOVE_ID"), org.jooq.impl.SQLDataType.VARCHAR(128).nullable(false), this, "");

    /**
     * Create a <code>WEBSERVICE.YAKADEX_MOVES</code> table reference
     */
    public YakadexMoves() {
        this(DSL.name("YAKADEX_MOVES"), null);
    }

    /**
     * Create an aliased <code>WEBSERVICE.YAKADEX_MOVES</code> table reference
     */
    public YakadexMoves(String alias) {
        this(DSL.name(alias), YAKADEX_MOVES);
    }

    /**
     * Create an aliased <code>WEBSERVICE.YAKADEX_MOVES</code> table reference
     */
    public YakadexMoves(Name alias) {
        this(alias, YAKADEX_MOVES);
    }

    private YakadexMoves(Name alias, Table<YakadexMovesRecord> aliased) {
        this(alias, aliased, null);
    }

    private YakadexMoves(Name alias, Table<YakadexMovesRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> YakadexMoves(Table<O> child, ForeignKey<O, YakadexMovesRecord> key) {
        super(child, key, YAKADEX_MOVES);
    }

    @Override
    public Schema getSchema() {
        return Webservice.WEBSERVICE;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.CONSTRAINT_INDEX_B, Indexes.CONSTRAINT_INDEX_BC, Indexes.PRIMARY_KEY_B);
    }

    @Override
    public UniqueKey<YakadexMovesRecord> getPrimaryKey() {
        return Keys.CONSTRAINT_B;
    }

    @Override
    public List<UniqueKey<YakadexMovesRecord>> getKeys() {
        return Arrays.<UniqueKey<YakadexMovesRecord>>asList(Keys.CONSTRAINT_B);
    }

    @Override
    public List<ForeignKey<YakadexMovesRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<YakadexMovesRecord, ?>>asList(Keys.CONSTRAINT_BC, Keys.CONSTRAINT_BCC);
    }

    public Yakadex yakadex() {
        return new Yakadex(this, Keys.CONSTRAINT_BC);
    }

    public Move move() {
        return new Move(this, Keys.CONSTRAINT_BCC);
    }

    @Override
    public YakadexMoves as(String alias) {
        return new YakadexMoves(DSL.name(alias), this);
    }

    @Override
    public YakadexMoves as(Name alias) {
        return new YakadexMoves(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public YakadexMoves rename(String name) {
        return new YakadexMoves(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public YakadexMoves rename(Name name) {
        return new YakadexMoves(name, null);
    }

    // -------------------------------------------------------------------------
    // Row3 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row3<Integer, String, String> fieldsRow() {
        return (Row3) super.fieldsRow();
    }
}
