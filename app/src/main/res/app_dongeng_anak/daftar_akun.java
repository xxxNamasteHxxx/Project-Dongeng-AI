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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.namasteh.app_dongeng_anak.R;
import com.example.namasteh.app_dongeng_anak.menu_awal;

import java.util.HashMap;

public class daftar_akun extends AppCompatActivity implements View.OnClickListener {

    private EditText txtnama,txtalamat,txtusia,txtpassword, txtid, txtemail, txtstatus;
    private Spinner txtjeniskelamin;
    private Button btndaftar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_akun);

        txtnama = (EditText)findViewById(R.id.edtnama);
        txtalamat = (EditText)findViewById(R.id.edtalamat);
        txtusia = (EditText)findViewById(R.id.edtusia);
        txtpassword = (EditText)findViewById(R.id.edtpassword);
        txtjeniskelamin = (Spinner)findViewById(R.id.edtjeniskelamin);
        txtid = (EditText)findViewById(R.id.edtid);
        txtemail = (EditText)findViewById(R.id.edtemail);
        txtstatus = (EditText)findViewById(R.id.edtstatus);

        txtid.setVisibility(View.INVISIBLE);

        btndaftar = (Button)findViewById(R.id.btndaftarakun);

        btndaftar.setOnClickListener(this);
    }

    private void addakun(){

        final String nama = txtnama.getText().toString().trim();
        final String alamat = txtalamat.getText().toString().trim();
        final String id = txtid.getText().toString().trim();
        final String usia = txtusia.getText().toString().trim();
        final String password = txtpassword.getText().toString().trim();
        final String jeniskelamin = txtjeniskelamin.getSelectedItem().toString().trim();
        final String email = txtemail.getText().toString().trim();
        final String status = txtstatus.getText().toString().trim();

        class Addakun extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(daftar_akun.this,"Menambahkan...","Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(daftar_akun.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(konfigurasi.KEY_AKUN_NAMA,nama);
                params.put(konfigurasi.KEY_AKUN_EMAIL,email);
                params.put(konfigurasi.KEY_AKUN_ALAMAT,alamat);
                params.put(konfigurasi.KEY_AKUN_USIA,usia);
                params.put(konfigurasi.KEY_AKUN_ID,id);
                params.put(konfigurasi.KEY_AKUN_JENIS_KELAMIN,jeniskelamin);
                params.put(konfigurasi.KEY_AKUN_PASSWORD,password);
                params.put(konfigurasi.KEY_AKUN_STATUS,status);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(konfigurasi.URL_ADD_AKUN, params);
                return res;
            }
        }

        Addakun akun = new Addakun();
        akun.execute();
    }

    @Override
    public void onClick(View v) {
        if(v == btndaftar){
            addakun();
            showNotif();
            startActivity(new Intent(this,menu_awal.class));
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
                .setContentTitle("Terimakasi Telah Mendaftar")
                .setContentText("Admin akan meninjau data anda");

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(115, builder.build());
    }
}
