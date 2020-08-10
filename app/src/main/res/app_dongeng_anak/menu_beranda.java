package com.example.namasteh.app_dongeng_anak;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;

import static com.example.namasteh.app_dongeng_anak.menu_awal.TAG_ID;
import static com.example.namasteh.app_dongeng_anak.menu_awal.session_status;

public class menu_beranda extends AppCompatActivity {

    private TextView txtid,txtnama;
    private Button btnlogout;
    private LinearLayout lltuliscerita, llgambar, llprofil,llrequestcerita,llfeedback,llbacacerita, llceritasaya,
    lllike,llcaricerita, llrekomcerita, lltrend;
    private String id, nama;
    public final static String TAG_NAMA= "nama";
    public final static String TAG_ID= "id";
    private SharedPreferences sharedpreferences;
    public static final String my_shared_preferences = "my_shared_preferences";
    public static final String session_status = "session_status";
    Boolean session = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_beranda);

        txtid = (TextView)findViewById(R.id.edttxtid1);
        txtnama = (TextView)findViewById(R.id.edttxtnama1);
        btnlogout = (Button)findViewById(R.id.btn_logout);
        lltuliscerita = (LinearLayout)findViewById(R.id.lltuliscerita);
//        llbacacerita = (LinearLayout)findViewById(R.id.llbacacerita);
        llgambar = (LinearLayout)findViewById(R.id.llgambar);
        llprofil =(LinearLayout)findViewById(R.id.btnprofilpenulis);
        llrequestcerita =(LinearLayout)findViewById(R.id.llrequestcerita);
        llfeedback =(LinearLayout)findViewById(R.id.llfeedback);
        llceritasaya =(LinearLayout)findViewById(R.id.llceritasaya);
        llcaricerita =(LinearLayout)findViewById(R.id.llcaricerita);
        llrekomcerita =(LinearLayout)findViewById(R.id.llrekomcerita);
        lltrend =(LinearLayout)findViewById(R.id.lltrend);

        sharedpreferences = getSharedPreferences(menu_beranda.my_shared_preferences, Context.MODE_PRIVATE);

        id = getIntent().getStringExtra(TAG_ID);
        nama = getIntent().getStringExtra(TAG_NAMA);
        txtnama.setText("Hai, " + nama);
        txtid.setText("ID, " + id);
        session = sharedpreferences.getBoolean(session_status, false);
        id = sharedpreferences.getString(TAG_ID, null);
        nama = sharedpreferences.getString(TAG_NAMA, null);


        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean(session_status, false);
                editor.putString(TAG_ID, null);
                editor.putString(TAG_NAMA, null);
                editor.commit();
                Intent intent = new Intent(menu_beranda.this, menu_awal.class);
                finish();
                startActivity(intent);
            }
        });

        lltuliscerita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lltulisancerita = new Intent(menu_beranda.this,menu_tulis_cerita.class);
                startActivity(lltulisancerita);
            }
        });

        llgambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent llgambar = new Intent(menu_beranda.this,menu_menggambar.class);
                startActivity(llgambar);
            }
        });

        llprofil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent llprofil = new Intent(menu_beranda.this,menu_profile.class);
                llprofil.putExtra(konfigurasi.AKUN_ID,id);
                startActivity(llprofil);
            }
        });

        llrequestcerita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent llrequestcerita = new Intent(menu_beranda.this,menu_request_cerita.class);
                startActivity(llrequestcerita);
            }
        });

        llfeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent llfeedback = new Intent(menu_beranda.this,menu_feedback.class);
                startActivity(llfeedback);
            }
        });

//        llbacacerita.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent llbacacerita = new Intent(menu_beranda.this,menu_semua_cerita.class);
//                startActivity(llbacacerita);
//            }
//        });

        llceritasaya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent llceritasaya = new Intent(menu_beranda.this,menu_beranda_laporan_cerita.class);
                llceritasaya.putExtra(konfigurasi.CERITA_SAYA_ID,id);
                startActivity(llceritasaya);
            }
        });

        llcaricerita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent llcaricerita = new Intent(menu_beranda.this,menu_cari_cerita.class);
                startActivity(llcaricerita);
            }
        });

        llrekomcerita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent llcaricerita = new Intent(menu_beranda.this,menu_rekom_cerita.class);
                startActivity(llcaricerita);
            }
        });

        lltrend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lltrend = new Intent(menu_beranda.this,menu_trend_cerita.class);
                startActivity(lltrend);
            }
        });
    }
}
