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

public class menu_trend_cerita_lengkap extends AppCompatActivity {
    private ListView listView;
    //    private TextView txtcsid,txtcsjudul,txtcstipe_cerita,txtcsidposting;
    private String JSON_STRING;
    private String tcid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_trend_cerita_lengkap);

        listView = (ListView) findViewById(R.id.listView);
//        listView.setOnItemClickListener(this);
        Intent intent = getIntent();
        tcid = intent.getStringExtra(konfigurasi.POSTING_CERITA_TIPE_CERITA);
        getJSON();
    }

    private void showpostingcerita(){
        JSONObject jsonObject = null;

        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_POSTING_CERITA_JSON_ARRAY);
            Toast.makeText(getApplicationContext(), "Length array: "+result.length(), Toast.LENGTH_SHORT).show();
            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String tipe_cerita = jo.getString(konfigurasi.TAG_POSTING_CERITA_TIPE_CERITA);
                String id = jo.getString(konfigurasi.TAG_POSTING_CERITA_ID);
                String judul_cerita = jo.getString(konfigurasi.TAG_POSTING_CERITA_JUDUL);

                HashMap<String,String> postingcerita = new HashMap<>();
                postingcerita.put(konfigurasi.TAG_POSTING_CERITA_TIPE_CERITA,tipe_cerita);
                postingcerita.put(konfigurasi.TAG_POSTING_CERITA_ID,id);
                postingcerita.put(konfigurasi.TAG_POSTING_CERITA_JUDUL,judul_cerita);
                list.add(postingcerita);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                menu_trend_cerita_lengkap.this, list, R.layout.list_trend_cerita_lengkap,
                new String[]{konfigurasi.TAG_POSTING_CERITA_TIPE_CERITA,
                        konfigurasi.TAG_POSTING_CERITA_JUDUL,
                        konfigurasi.TAG_POSTING_CERITA_ID},
                new int[]{R.id.tipe_ceritax,R.id.judulx,R.id.idx});

        listView.setAdapter(adapter);
//        txtcsid.setText(id);
    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(menu_trend_cerita_lengkap.this,
                        "Mengambil Data","Mohon Tunggu...",
                        false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showpostingcerita();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(konfigurasi.URL_TAMPIL_TREND_CERITA_LENGKAP,tcid);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
}
