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

public class menu_rekom_cerita extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView listView;
    //    private TextView txtcsid,txtcsjudul,txtcstipe_cerita,txtcsidposting;
    private String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_rekom_cerita);
        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
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
                String id_posting = jo.getString(konfigurasi.TAG_SUKA_POSTING_ID_POSTING);
                String id = jo.getString(konfigurasi.TAG_SUKA_POSTING_ID);
                String suka = jo.getString(konfigurasi.TAG_SUKA_POSTING_SUKA);
                String id_suka = jo.getString(konfigurasi.TAG_SUKA_POSTING_ID_SUKA);
                String judul_cerita = jo.getString(konfigurasi.TAG_SUKA_POSTING_JUDUL_CERITA);

                HashMap<String,String> postingcerita = new HashMap<>();
                postingcerita.put(konfigurasi.TAG_SUKA_POSTING_ID,id);
                postingcerita.put(konfigurasi.TAG_SUKA_POSTING_ID_POSTING,id_posting);
                postingcerita.put(konfigurasi.TAG_SUKA_POSTING_SUKA,suka);
                postingcerita.put(konfigurasi.TAG_SUKA_POSTING_ID_SUKA,id_suka);
                postingcerita.put(konfigurasi.TAG_SUKA_POSTING_JUDUL_CERITA,judul_cerita);
                list.add(postingcerita);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                menu_rekom_cerita.this, list, R.layout.list_rekom_cerita,
                new String[]{konfigurasi.TAG_SUKA_POSTING_ID_POSTING,
                        konfigurasi.TAG_SUKA_POSTING_ID,
                        konfigurasi.TAG_SUKA_POSTING_SUKA,
                        konfigurasi.TAG_SUKA_POSTING_ID_SUKA,
                        konfigurasi.TAG_SUKA_POSTING_JUDUL_CERITA},
                new int[]{R.id.xxid_posting, R.id.xxid, R.id.xxsuka, R.id.xxid_suka, R.id.xxjudul});

        listView.setAdapter(adapter);
//        txtcsid.setText(id);
    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(menu_rekom_cerita.this,
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
                String s = rh.sendGetRequest(konfigurasi.URL_TAMPIL_REKOM_CERITA);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, menu_baca_cerita1.class);
        HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
        String sid = map.get(konfigurasi.TAG_SUKA_POSTING_ID_POSTING).toString();
        intent.putExtra(konfigurasi.POSTING_CERITA_ID,sid);
        startActivity(intent);
    }
}
