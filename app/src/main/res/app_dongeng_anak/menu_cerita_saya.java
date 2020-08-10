package com.example.namasteh.app_dongeng_anak;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
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
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

public class menu_cerita_saya extends AppCompatActivity implements ListView.OnItemClickListener{
    private ListView listView;
//    private TextView txtcsid,txtcsjudul,txtcstipe_cerita,txtcsidposting;
    private String JSON_STRING;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_cerita_saya);
        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        Intent intent = getIntent();
        id = intent.getStringExtra(konfigurasi.CERITA_SAYA_ID);
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
                String id = jo.getString(konfigurasi.TAG_POSTING_CERITA_ID);
                String id_posting = jo.getString(konfigurasi.TAG_POSTING_CERITA_ID_POSTING);
                String tipe_cerita = jo.getString(konfigurasi.TAG_POSTING_CERITA_TIPE_CERITA);
                String judul = jo.getString(konfigurasi.TAG_POSTING_CERITA_JUDUL);
                String tgl_posting = jo.getString(konfigurasi.TAG_POSTING_CERITA_TGL_POSTING);

                HashMap<String,String> postingcerita = new HashMap<>();
                postingcerita.put(konfigurasi.TAG_POSTING_CERITA_ID,id);
                postingcerita.put(konfigurasi.TAG_POSTING_CERITA_ID_POSTING,id_posting);
                postingcerita.put(konfigurasi.TAG_POSTING_CERITA_TIPE_CERITA,tipe_cerita);
                postingcerita.put(konfigurasi.TAG_POSTING_CERITA_JUDUL,judul);
                postingcerita.put(konfigurasi.TAG_POSTING_CERITA_TGL_POSTING,tgl_posting);
                list.add(postingcerita);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                menu_cerita_saya.this, list, R.layout.list_cerita_saya,
                new String[]{konfigurasi.TAG_POSTING_CERITA_ID,
                        konfigurasi.TAG_POSTING_CERITA_TIPE_CERITA,
                        konfigurasi.TAG_POSTING_CERITA_JUDUL,
                        konfigurasi.TAG_POSTING_CERITA_ID_POSTING,
                konfigurasi.TAG_POSTING_CERITA_TGL_POSTING},
                new int[]{R.id.id, R.id.tipe_cerita, R.id.judul, R.id.id_posting, R.id.tgl_posting});

        listView.setAdapter(adapter);
//        txtcsid.setText(id);
    }

    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(menu_cerita_saya.this,
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
                String s = rh.sendGetRequestParam(konfigurasi.URL_TAMPIL_CERITA_SAYA,id);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, menu_baca_cerita_saya.class);
        HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
        String sid = map.get(konfigurasi.TAG_POSTING_CERITA_ID_POSTING).toString();
        intent.putExtra(konfigurasi.POSTING_CERITA_ID,sid);
        startActivity(intent);
    }

}
