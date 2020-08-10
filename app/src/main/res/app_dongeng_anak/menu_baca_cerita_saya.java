package com.example.namasteh.app_dongeng_anak;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class menu_baca_cerita_saya extends AppCompatActivity implements View.OnClickListener {

    private EditText edtisicerita,edtjudulpostingcerita,edttipeceritapostingcerita,
            edtidpostingcerita,edtstatuspostingcerita,edtididposting;
    private Button btnupdate;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_baca_cerita_saya);

        edtisicerita = (EditText)findViewById(R.id.edtisicerita);
        edtidpostingcerita = (EditText)findViewById(R.id.edtidpostingcerita);
        edtididposting = (EditText)findViewById(R.id.edtididposting);
        edtjudulpostingcerita = (EditText)findViewById(R.id.edtjudulpostingcerita);
        edtstatuspostingcerita = (EditText)findViewById(R.id.edtstatuspostingcerita);
        edttipeceritapostingcerita = (EditText)findViewById(R.id.edttipeceritapostingcerita);

        btnupdate=(Button)findViewById(R.id.btnupdatecerita);

//        edtidpostingcerita.setVisibility(View.INVISIBLE);
//        edtididposting.setVisibility(View.INVISIBLE);

        Intent intent = getIntent();
        id = intent.getStringExtra(konfigurasi.POSTING_CERITA_ID);

        btnupdate.setOnClickListener(this);
        getEmployee();
    }

    private void getEmployee(){
        class GetEmployee extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(menu_baca_cerita_saya.this,"Fetching...","Wait...",false,false);
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
            String id_posting = c.getString(konfigurasi.TAG_POSTING_CERITA_ID_POSTING);
            String tipe_cerita = c.getString(konfigurasi.TAG_POSTING_CERITA_TIPE_CERITA);
            String judul = c.getString(konfigurasi.TAG_POSTING_CERITA_JUDUL);
            String isi_cerita = c.getString(konfigurasi.TAG_POSTING_CERITA_ISI_CERITA);
            String status = c.getString(konfigurasi.TAG_POSTING_CERITA_STATUS);

            edtidpostingcerita.setText(ida);
            edtididposting.setText(id_posting);
            edttipeceritapostingcerita.setText(tipe_cerita);
            edtjudulpostingcerita.setText(judul);
            edtisicerita.setText(isi_cerita);
            edtstatuspostingcerita.setText(status);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void updateEmployee(){
        final String idz = edtidpostingcerita.getText().toString().trim();
        final String id_posting = edtididposting.getText().toString().trim();
        final String tipe_cerita = edttipeceritapostingcerita.getText().toString().trim();
        final String judul = edtjudulpostingcerita.getText().toString().trim();
        final String isi = edtisicerita.getText().toString().trim();
        final String status = edtstatuspostingcerita.getText().toString().trim();

        class UpdateEmployee extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(menu_baca_cerita_saya.this,"Updating...",
                        "Wait...",
                        false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(menu_baca_cerita_saya.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(konfigurasi.KEY_POSTING_CERITA_ID,idz);
                hashMap.put(konfigurasi.KEY_POSTING_CERITA_ID_POSTING,id_posting);
                hashMap.put(konfigurasi.KEY_POSTING_CERITA_TIPE_CERITA,tipe_cerita);
                hashMap.put(konfigurasi.KEY_POSTING_CERITA_JUDUL,judul);
                hashMap.put(konfigurasi.KEY_POSTING_CERITA_ISI_CERITA,isi);
                hashMap.put(konfigurasi.KEY_POSTING_CERITA_STATUS,status);

                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest(konfigurasi.URL_UPDATE_CERITA_SAYA,hashMap);

                return s;
            }
        }

        UpdateEmployee ue = new UpdateEmployee();
        ue.execute();
    }

    @Override
    public void onClick(View v) {
        if(v == btnupdate){
            updateEmployee();
        }
    }
}
