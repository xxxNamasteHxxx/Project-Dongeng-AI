package com.example.namasteh.app_dongeng_anak;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class laporan_request extends AppCompatActivity {

    private ListView listView;
    //    private TextView txtcsid,txtcsjudul,txtcstipe_cerita,txtcsidposting;
    private String JSON_STRING;
    private String xid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan_request);

        listView = (ListView) findViewById(R.id.listView);
        Intent intent = getIntent();
        xid = intent.getStringExtra(konfigurasi.REQUEST_SAYA);
        getJSON();
    }

    private void showrequest(){
        JSONObject jsonObject = null;

        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_REQUEST_CERITA_JSON_ARRAY);
            Toast.makeText(getApplicationContext(), "Length array: "+result.length(), Toast.LENGTH_SHORT).show();
            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String id = jo.getString(konfigurasi.TAG_REQUEST_CERITA_ID);
                String judul_cerita = jo.getString(konfigurasi.TAG_REQUEST_CERITA_JUDUL);
                String tipe_cerita = jo.getString(konfigurasi.TAG_REQUEST_CERITA_TIPE);
                String status = jo.getString(konfigurasi.TAG_REQUEST_CERITA_STATUS);
                String id_request = jo.getString(konfigurasi.TAG_REQUEST_CERITA_ID_REQUEST);

                HashMap<String,String> postingcerita = new HashMap<>();
                postingcerita.put(konfigurasi.TAG_REQUEST_CERITA_ID,id);
                postingcerita.put(konfigurasi.TAG_REQUEST_CERITA_JUDUL,judul_cerita);
                postingcerita.put(konfigurasi.TAG_REQUEST_CERITA_TIPE,tipe_cerita);
                postingcerita.put(konfigurasi.TAG_REQUEST_CERITA_STATUS,status);
                postingcerita.put(konfigurasi.TAG_REQUEST_CERITA_ID_REQUEST,id_request);
                list.add(postingcerita);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                laporan_request.this, list, R.layout.list_request_cerita,
                new String[]{konfigurasi.TAG_REQUEST_CERITA_ID,
                        konfigurasi.TAG_REQUEST_CERITA_JUDUL,
                        konfigurasi.TAG_REQUEST_CERITA_TIPE,
                        konfigurasi.TAG_REQUEST_CERITA_STATUS,
                        konfigurasi.TAG_REQUEST_CERITA_ID_REQUEST},
                new int[]{R.id.xid, R.id.xjudulcerita, R.id.xtipecerita, R.id.xstatus, R.id.xid_request});

        listView.setAdapter(adapter);
    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(laporan_request.this,
                        "Mengambil Data","Mohon Tunggu...",
                        false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showrequest();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(konfigurasi.URL_TAMPIL_REQUEST_CERITA,xid);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
}
