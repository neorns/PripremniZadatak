package com.example.androiddevelopment.pripremnizadatak.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by androiddevelopment on 10.3.17..
 */

@DatabaseTable(tableName = Glumac.TABLE_NAME_GLUMAC)
public class Glumac {
    public static final String TABLE_NAME_GLUMAC = "glumci";

    public static final String FIELD_NAME_ID     = "id";
    public static final String FIELD_NAME_IME   = "ime";

    public static final String FIELD_NAME_BIOGRAFIJA   = "biografija";
    public static final String FIELD_NAME_OCENA   = "ocena";
    public static final String FIELD_NAME_DATUMRODJENJA   = "datumrodjenja";


    @DatabaseField(columnName = FIELD_NAME_ID, generatedId = true)
    private int mId;

    @DatabaseField(columnName = FIELD_NAME_IME)
    private String mIme;


    @DatabaseField(columnName = FIELD_NAME_BIOGRAFIJA)
    private String mBiografija;

    @DatabaseField(columnName = FIELD_NAME_OCENA)
    private double mOcena;

    @DatabaseField(columnName = FIELD_NAME_DATUMRODJENJA)
    private String mDatumRodjenja;

    @ForeignCollectionField(foreignFieldName = "glumac")
    private ForeignCollection<Film> filmovi ;
    public Glumac() {
    }

    public int getmId() {
        return mId;
    }

    public String getmIme() {
        return mIme;
    }

    public void setmIme(String mIme) {
        this.mIme = mIme;
    }


    public String getmBiografija() {
        return mBiografija;
    }

    public void setmBiografija(String mBiografija) {
        this.mBiografija = mBiografija;
    }

    public double getmOcena() {
        return mOcena;
    }

    public void setmOcena(double mOcena) {
        this.mOcena = mOcena;
    }

    public String getmDatumRodjenja() {
        return mDatumRodjenja;
    }

    public void setmDatumRodjenja(String mDatumRodjenja) {
        this.mDatumRodjenja = mDatumRodjenja;
    }

    public ForeignCollection<Film> getFilmovi() {
        return filmovi;
    }

    public void setFilmovi(ForeignCollection<Film> filmovi) {
        this.filmovi = filmovi;
    }

    @Override
    public String toString() {
        return  mIme;
    }
}
