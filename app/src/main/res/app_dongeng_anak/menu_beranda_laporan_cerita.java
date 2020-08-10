package com.example.namasteh.app_dongeng_anak;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class menu_beranda_laporan_cerita extends AppCompatActivity {

    public final static String TAG_ID= "id";
    private LinearLayout bcsllike,bcsceritasaya,bcsubah,bcsrequest,bcsgraph;
    String id;
    private SharedPreferences sharedpreferences;
    public static final String my_shared_preferences = "my_shared_preferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_beranda_laporan_cerita);

        bcsceritasaya = (LinearLayout)findViewById(R.id.bcsceritasaya);
        bcsllike = (LinearLayout)findViewById(R.id.bcslllike);
        bcsubah = (LinearLayout)findViewById(R.id.bcsubah);
        bcsrequest = (LinearLayout)findViewById(R.id.bcsrequest);
        bcsgraph = (LinearLayout)findViewById(R.id.bcsgraph);

        sharedpreferences = getSharedPreferences(menu_beranda_laporan_cerita.my_shared_preferences, Context.MODE_PRIVATE);
        id = sharedpreferences.getString(TAG_ID, null);

        bcsceritasaya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent llceritasaya = new Intent(menu_beranda_laporan_cerita.this,menu_laporan_cerita_saya.class);
                llceritasaya.putExtra(konfigurasi.CERITA_SAYA_ID,id);
                startActivity(llceritasaya);
            }
        });

        bcsubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent llceritasaya = new Intent(menu_beranda_laporan_cerita.this,menu_cerita_saya.class);
                llceritasaya.putExtra(konfigurasi.CERITA_SAYA_ID,id);
                startActivity(llceritasaya);
            }
        });

        bcsllike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lllike = new Intent(menu_beranda_laporan_cerita.this,menu_like_saya.class);
                lllike.putExtra(konfigurasi.LIKE_POSTING,id);
                startActivity(lllike);
            }
        });

        bcsrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent llrequest = new Intent(menu_beranda_laporan_cerita.this,laporan_request.class);
                llrequest.putExtra(konfigurasi.REQUEST_SAYA,id);
                startActivity(llrequest);
            }
        });

        bcsgraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent llgraph = new Intent(menu_beranda_laporan_cerita.this,menu_graph.class);
                startActivity(llgraph);
            }
        });

    }
}
