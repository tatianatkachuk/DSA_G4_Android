package com.example.android_whostolesantasbeard;

import com.example.android_whostolesantasbeard.entities.UserCredentials;

import java.util.List;
import java.util.Observable;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface IWSSBService {
    @POST("user/signUp")
    Call<UserCredentials> registerAccount(@Body UserCredentials credentials);

    @POST("user/logIn")
    Call<User> loginUser(@Body User user);

    @GET("user/logIn/store") Observable<List<Object>>();
}