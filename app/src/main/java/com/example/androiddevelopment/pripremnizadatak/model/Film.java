package com.example.androiddevelopment.pripremnizadatak.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import static com.example.androiddevelopment.pripremnizadatak.model.Glumac.FIELD_NAME_IME;

/**
 * Created by androiddevelopment on 10.3.17..
 */

@DatabaseTable(tableName = Film.TABLE_NAME_FILM)
public class Film {
    public static final String TABLE_NAME_FILM = "filmovi";

    public static final String FIELD_NAME_ID     = "id";
    public static final String FIELD_NAME_NAZIV     = "naziv";
    @DatabaseField(columnName = FIELD_NAME_ID, generatedId = true)
    private int mId;

    @DatabaseField(columnName = FIELD_NAME_NAZIV)
    private String mNaziv;


    @DatabaseField(foreign = true, foreignAutoRefresh = true, canBeNull = false)
    private Glumac glumac;



    public Film() {
    }



}
