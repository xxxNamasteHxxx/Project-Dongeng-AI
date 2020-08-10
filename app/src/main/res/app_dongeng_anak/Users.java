package com.example.namasteh.app_dongeng_anak;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Users {

    @SerializedName("id") private String Id;
    @SerializedName("tipe_cerita") private String Tipe_cerita;
    @SerializedName("id_posting") private String Id_posting;
    @SerializedName("judul") private String Judul;
    @SerializedName("tgl_posting")private String Tgl_posting;

    public String getTgl_posting() {
        return Tgl_posting;
    }

    public String getTipe_cerita() {
        return Tipe_cerita;
    }

    public String getId_posting() {
        return Id_posting;
    }

    public String getId() {
        return Id;
    }

    public String getJudul() {
        return Judul;
    }
}
