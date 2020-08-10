package com.example.namasteh.app_dongeng_anak;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.namasteh.app_dongeng_anak.menu_beranda.TAG_ID;

public class menu_profile extends AppCompatActivity {

    private TextView pid,pusername,palamat,pusia,pjeniskelamin,ppassword,pemail;
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_profile);

        pid = (TextView) findViewById(R.id.ppid);
        pusia = (TextView) findViewById(R.id.pusia);
        pjeniskelamin = (TextView) findViewById(R.id.pjeniskelamin);
        ppassword = (TextView) findViewById(R.id.ppassword);
        palamat = (TextView) findViewById(R.id.palamat);
        pusername = (TextView) findViewById(R.id.pusername);
        pemail = (TextView) findViewById(R.id.pemail);

        Intent intent = getIntent();

//        pid.setText(getIntent().getStringExtra("id"));
        id = intent.getStringExtra(konfigurasi.AKUN_ID);

        pid.setText(id);
        getAkun();
    }

    private void getAkun(){
        class GetAkun extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(menu_profile.this,"Fetching...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showAkun(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(konfigurasi.URL_TAMPIL_AKUN,id);
                return s;
            }
        }
        GetAkun ga = new GetAkun();
        ga.execute();
    }
    private void showAkun(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray akun = jsonObject.getJSONArray(konfigurasi.TAG_AKUN_JSON_ARRAY);
                JSONObject c = akun.getJSONObject(0);
                String nama = c.getString(konfigurasi.TAG_AKUN_NAMA);
                String email = c.getString(konfigurasi.TAG_AKUN_EMAIL);
                String alamat = c.getString(konfigurasi.TAG_AKUN_ALAMAT);
                String jenis_kelamin = c.getString(konfigurasi.TAG_AKUN_JENIS_KELAMIN);
                String usia = c.getString(konfigurasi.TAG_AKUN_USIA);
                String password = c.getString(konfigurasi.TAG_AKUN_PASSWORD);

//            txtidpengarang.setText(id);
                pusername.setText(nama);
                pemail.setText(email);
                palamat.setText(alamat);
                pjeniskelamin.setText(jenis_kelamin);
                pusia.setText(usia);
                ppassword.setText(password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
