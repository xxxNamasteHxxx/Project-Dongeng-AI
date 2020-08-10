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
import android.speech.tts.TextToSpeech;
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
import java.util.Locale;

public class menu_baca_cerita extends AppCompatActivity implements View.OnClickListener{

    private TextView txtisicerita,txtidpengarang,txtjudulcerita,txttipecerita,getrate,txtidposting;
    private Button btnrating,btnplay;
    private AppCompatRatingBar ratingbar;
    private String id,id1;
    private TextToSpeech t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_baca_cerita);

        Intent intent = getIntent();

        id = intent.getStringExtra("id");

        txtidpengarang = (TextView)findViewById(R.id.txtidpengarang);
        txtisicerita = (TextView)findViewById(R.id.txtisicerita);
        txtjudulcerita = (TextView)findViewById(R.id.txtjudulcerita);
        txttipecerita = (TextView)findViewById(R.id.txttipecerita);
        txtidposting = (TextView)findViewById(R.id.txtidposting);
        getrate = (TextView)findViewById(R.id.rate);
        ratingbar = (AppCompatRatingBar)findViewById(R.id.penilaian);
        btnrating = (Button)findViewById(R.id.btnrating);
        btnplay = (Button)findViewById(R.id.btnplay);
        btnrating.setOnClickListener(this);

        txtidposting.setText(id);
        getEmployee();

        ratingbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                getrate.setText(""+rating);
            }
        });

        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    //t1.setLanguage(Locale.UK);
                    t1.setLanguage(new Locale("id","ID"));
                }
            }
        });

        btnplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = txtisicerita.getText().toString();
                Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

    }
    private void getEmployee(){
        class GetEmployee extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(menu_baca_cerita.this,"Fetching...","Wait...",false,false);
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

    private void addfeedback(){
        final String id1 = txtidpengarang.getText().toString().trim();
        final String id = txtidposting.getText().toString();
        final String judulcerita = txtjudulcerita.getText().toString().trim();
        final String suka = getrate.getText().toString().trim();

        class Addfeedback extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(menu_baca_cerita.this,"Menambahkan...","Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(menu_baca_cerita.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(konfigurasi.KEY_SUKA_POSTINGAN_ID,id1);
                params.put(konfigurasi.KEY_SUKA_POSTINGAN_ID_POSTING,id);
                params.put(konfigurasi.KEY_SUKA_POSTINGAN_JUDUL_CERITA,judulcerita);
                params.put(konfigurasi.KEY_SUKA_POSTINGAN_SUKA,suka);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(konfigurasi.URL_SUKA_POSTINGAN, params);
                return res;
            }
        }

        Addfeedback feedback = new Addfeedback();
        feedback.execute();
    }

    @Override
    public void onClick(View v) {
        if(v == btnrating){
            addfeedback();
            Intent intent = new Intent(menu_baca_cerita.this, menu_semua_cerita.class);
            startActivity(intent);
            Toast.makeText(getApplicationContext(), "Anda Memberikan Rating: "+ratingbar.getRating(), Toast.LENGTH_SHORT).show();
            showNotif();
            finish();
        }
    }

    private void showNotif() {
        String NOTIFICATION_CHANNEL_ID = "Aplikasi Dongeng Anak";
        Context context = this.getApplicationContext();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            String channelName = "Notif Like";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel mChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

        Intent mIntent = new Intent(this, menu_semua_cerita.class);
        Bundle bundle = new Bundle();
        bundle.putString("fromnotif", "notif");
        mIntent.putExtras(bundle);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.read)
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.read))
                .setTicker("notif starting")
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setLights(Color.RED, 3000, 3000)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setContentTitle("Notifikasi Rating")
                .setContentText("Terimakasih:"+id1);

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(115, builder.build());
    }
}
