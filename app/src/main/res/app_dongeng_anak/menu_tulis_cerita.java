package com.example.namasteh.app_dongeng_anak;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import static com.example.namasteh.app_dongeng_anak.menu_beranda.TAG_ID;
import static com.example.namasteh.app_dongeng_anak.menu_beranda.TAG_NAMA;
import static com.example.namasteh.app_dongeng_anak.menu_beranda.my_shared_preferences;
import static com.example.namasteh.app_dongeng_anak.menu_beranda.session_status;

public class menu_tulis_cerita extends AppCompatActivity implements View.OnClickListener {

    private Button speak;
    private TextToSpeech t1;
    private Button speaktotext,btntambahpostingcerita;
    private EditText speakinput,edtjudulpostingcerita,edtidpostingcerita,edtstatuspostingcerita;
    private Spinner edttipeceritapostingcerita;
    private final int REQ_CODE_SPEECH_INPUT = 100;
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
        setContentView(R.layout.activity_menu_tulis_cerita);

        speakinput = (EditText)findViewById(R.id.textspeechinput);
        edtidpostingcerita = (EditText)findViewById(R.id.edtidpostingcerita);
        edtjudulpostingcerita = (EditText)findViewById(R.id.edtjudulpostingcerita);
        edtstatuspostingcerita = (EditText)findViewById(R.id.edtstatuspostingcerita);
        edttipeceritapostingcerita = (Spinner) findViewById(R.id.edttipeceritapostingcerita);

//        edtidpostingcerita.setVisibility(View.INVISIBLE);
        speak = (Button) findViewById(R.id.btnspeech);
        speaktotext = (Button)findViewById(R.id.textsuara);
        btntambahpostingcerita = (Button)findViewById(R.id.btntambahpostingcerita);
        btntambahpostingcerita.setOnClickListener(this);


        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        id = getIntent().getStringExtra(TAG_ID);
        nama = getIntent().getStringExtra(TAG_NAMA);
        id = sharedpreferences.getString(TAG_ID, null);
        nama = sharedpreferences.getString(TAG_NAMA, null);
        session = sharedpreferences.getBoolean(session_status, false);
        edtidpostingcerita.setText(id);

        speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });

        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(new Locale("id","ID"));
                }
            }
        });

        speaktotext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = speakinput.getText().toString();
                Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

    }

    private void addpostingcerita(){
        promptSpeechInput();
        final String id = edtidpostingcerita.getText().toString().trim();
        final String tipe_cerita= edttipeceritapostingcerita.getSelectedItem().toString().trim();
        final String judul = edtjudulpostingcerita.getText().toString().trim();
        final String isi_cerita = speakinput.getText().toString().trim();
        final String status = edtstatuspostingcerita.getText().toString().trim();

        class Addpostingancerita extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(menu_tulis_cerita.this,"Menambahkan...","Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(menu_tulis_cerita.this,s,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(konfigurasi.KEY_POSTING_CERITA_ID,id);
                params.put(konfigurasi.KEY_POSTING_CERITA_TIPE_CERITA,tipe_cerita);
                params.put(konfigurasi.KEY_POSTING_CERITA_JUDUL,judul);
                params.put(konfigurasi.KEY_POSTING_CERITA_ISI_CERITA,isi_cerita);
                params.put(konfigurasi.KEY_POSTING_CERITA_STATUS,status);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(konfigurasi.URL_POSTING_CERITA, params);
                return res;
            }
        }

        Addpostingancerita postingcerita = new Addpostingancerita();
        postingcerita.execute();
    }

//    public void onPause(){
//        if(t1 !=null){
//            t1.stop();
//            t1.shutdown();
//        }
//        super.onPause();
//    }

    /**
     * Showing google speech input dialog
     * */
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    String txt = speakinput.getText().toString();
                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String combine = txt + result.get(0)+ ' ';
                    speakinput.setText(combine);
                }
                break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v == btntambahpostingcerita){
            addpostingcerita();
            if (session) {
                Intent intent = new Intent(menu_tulis_cerita.this, menu_beranda.class);
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
                .setContentTitle("Terimakasih Sudah Menulis")
                .setContentText("Admin akan meninjau tulisan anda sebelum di publish");

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(115, builder.build());
    }
}