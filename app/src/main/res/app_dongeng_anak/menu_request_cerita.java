package com.example.namasteh.app_dongeng_anak;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;

public class menu_request_cerita extends AppCompatActivity implements View.OnClickListener {

    private EditText edtidrequestcerita,edtjudulrequestcerita,edtstatusrequestcerita;
    private Spinner edttiperequestcerita;
    private Button btnrequestcerita;
    private String id, nama;
    public final static String TAG_NAMA= "nama";
    public final static String TAG_ID= "id";
    private SharedPreferences sharedpreferences;
    public static final String my_shared_preferences = "my_shared_preferences";
    public static final String session_status = "session_status";
    Boolean session = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_request_cerita);

        edtidrequestcerita = (EditText)findViewById(R.id.edtidrequestcerita);
        edtjudulrequestcerita = (EditText)findViewById(R.id.edtjudulrequestcerita);
        edttiperequestcerita = (Spinner)findViewById(R.id.edttiperequestcerita);
        edtstatusrequestcerita = (EditText)findViewById(R.id.edtstatusrequestcerita);
        btnrequestcerita = (Button)findViewById(R.id.btnrequestcerita);

        btnrequestcerita.setOnClickListener(this);

        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        id = getIntent().getStringExtra(TAG_ID);
        nama = getIntent().getStringExtra(TAG_NAMA);
        id = sharedpreferences.getString(TAG_ID, null);
        nama = sharedpreferences.getString(TAG_NAMA, null);
        session = sharedpreferences.getBoolean(session_status, false);
        edtidrequestcerita.setText(id);
    }

    private void addrequestcerita(){
        final String id = edtidrequestcerita.getText().toString().trim();
        final String judul_cerita = edtjudulrequestcerita.getText().toString().trim();
        final String tipe_cerita = edttiperequestcerita.getSelectedItem().toString().trim();
        final String status = edtstatusrequestcerita.getText().toString().trim();

        class Addrequestcerita extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(menu_request_cerita.this,"Menambahkan...","Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(menu_request_cerita.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(konfigurasi.KEY_REQUEST_CERITA_ID,id);
                params.put(konfigurasi.KEY_REQUEST_CERITA_JUDUL_CERITA,judul_cerita);
                params.put(konfigurasi.KEY_REQUEST_CERITA_STATUS,status);
                params.put(konfigurasi.KEY_REQUEST_CERITA_TIPE_CERITA,tipe_cerita);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(konfigurasi.URL_REQUEST_CERITA, params);
                return res;
            }
        }

        Addrequestcerita request_cerita= new Addrequestcerita();
        request_cerita.execute();
    }

    @Override
    public void onClick(View v) {
        if(v == btnrequestcerita){
            addrequestcerita();
            if (session) {
                Intent intent = new Intent(menu_request_cerita.this, menu_beranda.class);
                intent.putExtra(TAG_ID, id);
                intent.putExtra(TAG_NAMA, nama);
                showNotif();
                finish();
                startActivity(intent);
            }
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
                .setContentTitle("Notifikasi Request Cerita")
                .setContentText("Request anda akan ditinjau admin");

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(115, builder.build());
    }
}
