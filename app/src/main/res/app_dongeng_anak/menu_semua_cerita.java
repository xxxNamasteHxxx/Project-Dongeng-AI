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
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class menu_semua_cerita extends AppCompatActivity implements ListView.OnItemClickListener {
    private ListView listView;
//    private SearchView searchView;
    private String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_semua_cerita);

        listView = (ListView) findViewById(R.id.listView);
//        searchView = (SearchView) findViewById(R.id.searchview);
        listView.setOnItemClickListener(this);
        getJSON();
    }

    private void showpostingcerita(){
        JSONObject jsonObject = null;

        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
//        ArrayList<HashMap<String,Date>> listtgl = new ArrayList<HashMap<String, Date>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(konfigurasi.TAG_POSTING_CERITA_JSON_ARRAY);
            Toast.makeText(getApplicationContext(), "Length array: "+result.length(), Toast.LENGTH_SHORT).show();
            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String id = jo.getString(konfigurasi.TAG_POSTING_CERITA_ID);
                String id_posting = jo.getString(konfigurasi.TAG_POSTING_CERITA_ID_POSTING);
                String tipe_cerita = jo.getString(konfigurasi.TAG_POSTING_CERITA_TIPE_CERITA);
                String judul = jo.getString(konfigurasi.TAG_POSTING_CERITA_JUDUL);
//                SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                String time = new SimpleDateFormat(konfigurasi.TAG_POSTING_CERITA_TGL_POSTING).toString();
//                String tanggal = df2.format(new Date());
//                try {
//                    Date tgl = df2.parse(tgl_posting);
//                    HashMap<String, Date> tglcerita = new HashMap<>();
//                    tglcerita.put(konfigurasi.TAG_POSTING_CERITA_TGL_POSTING,tgl);
//                    listtgl.add(tglcerita);
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }

                HashMap<String,String> postingcerita = new HashMap<>();
                postingcerita.put(konfigurasi.TAG_POSTING_CERITA_ID,id);
                postingcerita.put(konfigurasi.TAG_POSTING_CERITA_ID_POSTING,id_posting);
                postingcerita.put(konfigurasi.TAG_POSTING_CERITA_TIPE_CERITA,tipe_cerita);
//                postingcerita.put(konfigurasi.TAG_POSTING_CERITA_TGL_POSTING,tanggal);
                postingcerita.put(konfigurasi.TAG_POSTING_CERITA_JUDUL,judul);
                list.add(postingcerita);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                menu_semua_cerita.this, list, R.layout.list_semua_cerita,
                new String[]{konfigurasi.TAG_POSTING_CERITA_ID,
                        konfigurasi.TAG_POSTING_CERITA_TIPE_CERITA,
                        konfigurasi.TAG_POSTING_CERITA_TGL_POSTING,
                        konfigurasi.TAG_POSTING_CERITA_JUDUL,
                        konfigurasi.TAG_POSTING_CERITA_ID_POSTING},
                new int[]{R.id.id, R.id.tipe_cerita, R.id.tgl_posting,R.id.judul, R.id.id_posting});

//        ListAdapter adapter1 = new SimpleAdapter(
//                menu_semua_cerita.this, listtgl, R.layout.list_semua_cerita,
//                new String[]{konfigurasi.TAG_POSTING_CERITA_TGL_POSTING},
//                new int[]{R.id.tgl_posting});

        listView.setAdapter(adapter);
//        listView.setAdapter(adapter1);
    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(menu_semua_cerita.this,"Mengambil Data",
                        "Mohon Tunggu...",
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
                String s = rh.sendGetRequest(konfigurasi.URL_TAMPIL_POSTING_CERITA);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, menu_baca_cerita.class);
        HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
        String sid = map.get(konfigurasi.TAG_POSTING_CERITA_ID_POSTING).toString();
        intent.putExtra(konfigurasi.POSTING_CERITA_ID,sid);
        startActivity(intent);
    }

}

