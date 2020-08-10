package com.example.namasteh.app_dongeng_anak;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class menu_like_saya extends AppCompatActivity{
    private ListView listView;
    private String JSON_STRING;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_like_saya);
        listView = (ListView) findViewById(R.id.listView);
//        listView.setOnItemClickListener(this);
        Intent intent = getIntent();
        id = intent.getStringExtra(konfigurasi.LIKE_POSTING);
        getJSON();
    }

    private void showpostingcerita(){
        JSONObject jsonObject = null;

        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_REQUEST_LIKE_JSON_ARRAY);
            Toast.makeText(getApplicationContext(), "Length array: "+result.length(), Toast.LENGTH_SHORT).show();
            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String id = jo.getString(konfigurasi.TAG_LIKE_ID);
                String judulcerita = jo.getString(konfigurasi.TAG_LIKE_JUDUL_CERITA);
                String id_suka = jo.getString(konfigurasi.TAG_LIKE_ID_SUKA);
                String suka = jo.getString(konfigurasi.TAG_LIKE_SUKA);

                HashMap<String,String> like = new HashMap<>();
                like.put(konfigurasi.TAG_LIKE_ID,id);
                like.put(konfigurasi.TAG_LIKE_JUDUL_CERITA,judulcerita);
                like.put(konfigurasi.TAG_LIKE_ID_SUKA,id_suka);
                like.put(konfigurasi.TAG_LIKE_SUKA,suka);
                list.add(like);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                menu_like_saya.this, list, R.layout.list_like,
                new String[]{konfigurasi.TAG_LIKE_ID,
                        konfigurasi.TAG_LIKE_JUDUL_CERITA,
                        konfigurasi.TAG_LIKE_ID_SUKA,
                        konfigurasi.TAG_LIKE_SUKA},
                new int[]{R.id.id, R.id.judulcerita,R.id.id_suka, R.id.suka});

        listView.setAdapter(adapter);
    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(menu_like_saya.this,
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
                String s = rh.sendGetRequestParam(konfigurasi.URL_TAMPIL_LIKE,id);
//                String s = rh.sendGetRequest(konfigurasi.URL_TAMPIL_LIKE);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
//
//
//
//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Intent intent = new Intent(this, menu_baca_laporan_posting_saya.class);
//        HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
//        String sid = map.get(konfigurasi.TAG_POSTING_CERITA_ID_POSTING).toString();
//        intent.putExtra(konfigurasi.POSTING_CERITA_ID,sid);
//        startActivity(intent);
//    }
}
