package com.example.thuchanhv3;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RegisterInterface {
//    String REGIURL = "http://192.168.1.8:8081/loginandroid/";
//    String REGIURL = "http://192.168.1.8:8081/app_android/";
      String REGIURL = "http://192.168.43.36:8081/app_android/";
    @FormUrlEncoded
//    @POST("simpleregister.php")
    @POST("register1.php")
    Call<String> getUserRegi(
            @Field("name") String name,
            @Field("class") String classRoom,
            @Field("address") String address,
            @Field("contact") String contact,
            @Field("user_name") String uname,
            @Field("password") String password,
            @Field("admin_id") Integer admin_id
    );
}
