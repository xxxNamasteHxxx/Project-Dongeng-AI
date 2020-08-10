package com.example.namasteh.app_dongeng_anak;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.AppCompatRatingBar;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class menu_baca_laporan_posting_saya extends AppCompatActivity{

    private TextView txtisicerita,txtidpengarang,txtjudulcerita,txttipecerita,getrate,txtidposting;
    private Button btnrating;
    private AppCompatRatingBar ratingbar;
    private String id,id1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_baca_laporan_posting_saya);

        Intent intent = getIntent();

//        id = intent.getStringExtra("id");

        id = intent.getStringExtra(konfigurasi.POSTING_CERITA_ID);

        txtidpengarang = (TextView)findViewById(R.id.txtidpengarang);
        txtisicerita = (TextView)findViewById(R.id.txtisicerita);
        txtjudulcerita = (TextView)findViewById(R.id.txtjudulcerita);
        txttipecerita = (TextView)findViewById(R.id.txttipecerita);
        txtidposting = (TextView)findViewById(R.id.txtidposting);
//        getrate = (TextView)findViewById(R.id.rate);
//        ratingbar = (AppCompatRatingBar)findViewById(R.id.penilaian);
//        btnrating = (Button)findViewById(R.id.btnrating);
//        btnrating.setOnClickListener(this);

        txtidposting.setText(id);
        getEmployee();

//        ratingbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
//            @Override
//            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
//                getrate.setText("Rating: "+rating);
//            }
//        });
    }
    private void getEmployee(){
        class GetEmployee extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(menu_baca_laporan_posting_saya.this,"Fetching...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showEmployee(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(konfigurasi.URL_TAMPIL_BACA_POSTING_CERITA,id);
                return s;
            }
        }
        GetEmployee ge = new GetEmployee();
        ge.execute();
    }
    private void showEmployee(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray posting_cerita = jsonObject.getJSONArray(konfigurasi.TAG_POSTING_CERITA_JSON_ARRAY);
            JSONObject c = posting_cerita.getJSONObject(0);
            String ida = c.getString(konfigurasi.TAG_POSTING_CERITA_ID);
            String tipe_cerita = c.getString(konfigurasi.TAG_POSTING_CERITA_TIPE_CERITA);
            String judul = c.getString(konfigurasi.TAG_POSTING_CERITA_JUDUL);
            String isi_cerita = c.getString(konfigurasi.TAG_POSTING_CERITA_ISI_CERITA);

            txtidpengarang.setText(ida);
            txttipecerita.setText(tipe_cerita);
            txtjudulcerita.setText(judul);
            txtisicerita.setText(isi_cerita);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

//    private void addfeedback(){
//        final String id1 = txtidpengarang.getText().toString().trim();
//        final String id = txtidposting.getText().toString();
//        final String judulcerita = txtjudulcerita.getText().toString().trim();
//        final String suka = getrate.getText().toString().trim();
//
//        class Addfeedback extends AsyncTask<Void,Void,String> {
//
//            ProgressDialog loading;
//
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//                loading = ProgressDialog.show(menu_baca_laporan_posting_saya.this,"Menambahkan...","Tunggu...",false,false);
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                super.onPostExecute(s);
//                loading.dismiss();
//                Toast.makeText(menu_baca_laporan_posting_saya.this,s,Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            protected String doInBackground(Void... v) {
//                HashMap<String,String> params = new HashMap<>();
//                params.put(konfigurasi.KEY_SUKA_POSTINGAN_ID,id1);
//                params.put(konfigurasi.KEY_SUKA_POSTINGAN_ID_POSTING,id);
//                params.put(konfigurasi.KEY_SUKA_POSTINGAN_JUDUL_CERITA,judulcerita);
//                params.put(konfigurasi.KEY_SUKA_POSTINGAN_SUKA,suka);
//
//                RequestHandler rh = new RequestHandler();
//                String res = rh.sendPostRequest(konfigurasi.URL_SUKA_POSTINGAN, params);
//                return res;
//            }
//        }
//
//        Addfeedback feedback = new Addfeedback();
//        feedback.execute();
//    }
}
