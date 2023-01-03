package com.example.android_whostolesantasbeard;

import com.example.android_whostolesantasbeard.entities.UserCredentials;

import java.util.List;
import java.util.Observable;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IWSSBService {
    @POST("user/signUp")
    Call<UserCredentials> registerAccount(@Body UserCredentials credentials);

    @POST("user/logIn")
    Call<User> loginUser(@Body User user);

    @GET("user/{userID}")
    Call<User> getUserbyID(@Path("userID") String userID);

    @GET ("user/{userName}")
    Call<User> getUserbyUserName(@Path("userName") String userName);

   /** @PATCH("user/{userID}/updateUser")
    Call<User> updateUserInfo(@Path("userID") String userID, @Body User user);**/

    @DELETE("user/delete/{userID}")
    Call<User> deleteUser(@Path("userID") String userID);
}