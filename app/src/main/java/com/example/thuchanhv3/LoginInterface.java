package com.example.thuchanhv3;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginInterface {
//    String LOGINURL = "http://192.168.1.8:8081/loginandroid/";
//    String LOGINURL = "http://192.168.1.8:8081/app_android/";
    String LOGINURL = "http://192.168.43.36:8081/app_android/";
    @FormUrlEncoded
//    @POST("simplelogin.php")
    @POST("login.php")
    Call<String> getUserLogin(

            @Field("username") String uname,
            @Field("password") String password
    );
}
