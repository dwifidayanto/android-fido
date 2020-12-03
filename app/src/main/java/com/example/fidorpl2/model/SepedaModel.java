package com.example.fahmirpl022018.model;

import android.os.Parcel;
import android.os.Parcelable;

public class SepedaModel implements Parcelable {
    private String ID;
    private String KODE;
    private String MERK;
    private String WARNA;
    private String JENIS;
    private String HARGA;
    private String GAMBAR;

    public SepedaModel(Parcel in) {
        ID = in.readString();
        KODE = in.readString();
        MERK = in.readString();
        WARNA = in.readString();
        JENIS = in.readString();
        HARGA = in.readString();
        GAMBAR = in.readString();
    }

    public static final Creator<SepedaModel> CREATOR = new Creator<SepedaModel>() {
        @Override
        public SepedaModel createFromParcel(Parcel in) {
            return new SepedaModel(in);
        }

        @Override
        public SepedaModel[] newArray(int size) {
            return new SepedaModel[size];
        }
    };

    public SepedaModel() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getKODE() {
        return KODE;
    }

    public void setKODE(String KODE) {
        this.KODE = KODE;
    }

    public String getMERK() {
        return MERK;
    }

    public void setMERK(String MERK) {
        this.MERK = MERK;
    }

    public String getWARNA() {
        return WARNA;
    }

    public void setWARNA(String WARNA) {
        this.WARNA = WARNA;
    }

    public String getJENIS() {
        return JENIS;
    }

    public void setJENIS(String JENIS) {
        this.JENIS = JENIS;
    }

    public String getHARGA() {
        return HARGA;
    }

    public void setHARGA(String HARGA) {
        this.HARGA = HARGA;
    }

    public String getGAMBAR() {
        return GAMBAR;
    }

    public void setGAMBAR(String GAMBAR) {
        this.GAMBAR = GAMBAR;
    }

    public static Creator<SepedaModel> getCREATOR() {
        return CREATOR;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ID);
        dest.writeString(KODE);
        dest.writeString(MERK);
        dest.writeString(WARNA);
        dest.writeString(JENIS);
        dest.writeString(HARGA);
        dest.writeString(GAMBAR);
    }
}
