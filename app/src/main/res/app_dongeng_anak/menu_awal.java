package com.example.namasteh.app_dongeng_anak;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.support.graphics.drawable.AnimationUtilsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class menu_awal extends AppCompatActivity {
    private ImageView bgapp, clover;
    private LinearLayout textsplash,textlogin,login;
    private Animation frombottom;
    private Button daftarakun,loginakun;
    private EditText edttxtusername,edttxtid,edtpassword;

    ProgressDialog pDialog;
    Intent intent;

    int success;
    ConnectivityManager conMgr;

    private String url = Server.LOGIN_AKUN+ "loginakun.php";

    private static final String TAG = menu_awal.class.getSimpleName();

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    public final static String TAG_NAMA = "nama";
    public final static String TAG_ID= "id";

    String tag_json_obj = "json_obj_req";

    SharedPreferences sharedpreferences;
    Boolean session = false;
    String id, nama;
    public static final String my_shared_preferences = "my_shared_preferences";
    public static final String session_status = "session_status";

    //    Animation bganim,clovernim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_awal);

        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        {
            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()) {
            } else {
                Toast.makeText(getApplicationContext(), "No Internet Connection",
                        Toast.LENGTH_LONG).show();
            }
        }

        daftarakun = (Button)findViewById(R.id.daftarakun);
        loginakun = (Button)findViewById(R.id.loginakun);

        bgapp = (ImageView)findViewById(R.id.bgapp);
        clover = (ImageView)findViewById(R.id.clover);

        textsplash = (LinearLayout) findViewById(R.id.textsplash);
        textlogin = (LinearLayout) findViewById(R.id.textlogin);
        login = (LinearLayout) findViewById(R.id.login);


        edttxtusername = (EditText)findViewById(R.id.edttxtusername);
        edttxtid = (EditText)findViewById(R.id.edttxtid);
        edtpassword = (EditText)findViewById(R.id.password);

//        bganim = AnimationUtils.loadAnimation(this,R.anim.bganim);
//        clovernim = AnimationUtils.loadAnimation(this,R.anim.clovernim);
        frombottom= AnimationUtils.loadAnimation(this,R.anim.frombottom);

        bgapp.animate().translationY(-1900).setDuration(800).setStartDelay(300);
        textsplash.animate().translationY(140).alpha(0).setDuration(800).setStartDelay(300);
        clover.animate().alpha(0).setDuration(800).setStartDelay(600);
        textlogin.startAnimation(frombottom);
        login.startAnimation(frombottom);

        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        session = sharedpreferences.getBoolean(session_status, false);
        id = sharedpreferences.getString(TAG_ID, null);
        nama = sharedpreferences.getString(TAG_NAMA, null);

        if (session) {
            Intent intent = new Intent(menu_awal.this, menu_beranda.class);
            intent.putExtra(TAG_ID, id);
            intent.putExtra(TAG_NAMA, nama);
            finish();
            startActivity(intent);
        }

        loginakun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = edttxtid.getText().toString();
                String password = edtpassword.getText().toString();
                String nama = edttxtusername.getText().toString();

                // mengecek kolom yang kosong
                if (id.trim().length() > 0 && password.trim().length() > 0 && nama.trim().length() > 0) {
                    if (conMgr.getActiveNetworkInfo() != null
                            && conMgr.getActiveNetworkInfo().isAvailable()
                            && conMgr.getActiveNetworkInfo().isConnected()) {
                        checkLogin(id, password, nama);
                    } else {
                        Toast.makeText(getApplicationContext() ,"No Internet Connection", Toast.LENGTH_LONG).show();
                    }
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext() ,"Kolom tidak boleh kosong", Toast.LENGTH_LONG).show();
                }
            }
        });


        daftarakun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent daftarakun = new Intent(menu_awal.this,daftar_akun.class);
                finish();
                startActivity(daftarakun);
            }
        });

    }

    private void checkLogin(final String id, final String password, final String nama) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Logging in ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Check for error node in json
                    if (success == 1) {
                        String nama = jObj.getString(TAG_NAMA);
                        String id = jObj.getString(TAG_ID);

                        Log.e("Successfully Login!", jObj.toString());

                        Toast.makeText(getApplicationContext(), jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                        // menyimpan login ke session
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putBoolean(session_status, true);
                        editor.putString(TAG_ID, id);
                        editor.putString(TAG_NAMA, nama);
                        editor.commit();

                        // Memanggil main activity
                        intent = new Intent(menu_awal.this, menu_beranda.class);
                        intent.putExtra(TAG_ID, id);
                        intent.putExtra(TAG_NAMA, nama);
                        finish();
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(),
                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", id);
                params.put("nama", nama);
                params.put("password", password);

                return params;
            }

        };

        // Adding request to request queue
        Controller_login.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
