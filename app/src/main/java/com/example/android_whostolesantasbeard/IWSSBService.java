package com.example.android_whostolesantasbeard;

import com.example.android_whostolesantasbeard.entities.UserCredentials;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IWSSBService {
    @POST("/users/signUp")
    Call registerAccount(@Body UserCredentials credentials);
}
