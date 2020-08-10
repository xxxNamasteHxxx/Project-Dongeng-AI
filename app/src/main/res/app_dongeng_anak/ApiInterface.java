package com.example.namasteh.app_dongeng_anak;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("caridata.php")
    Call< List<Users> > caridata(@Query("key")String keyword);
}
