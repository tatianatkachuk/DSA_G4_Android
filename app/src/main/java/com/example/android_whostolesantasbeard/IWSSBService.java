package com.example.android_whostolesantasbeard;

import com.example.android_whostolesantasbeard.entities.PasswordUpdate;
import com.example.android_whostolesantasbeard.entities.UserCredentials;

import java.util.List;
import java.util.Observable;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IWSSBService {
    @POST("user/signUp")
    Call<UserCredentials> registerAccount(@Body UserCredentials credentials);

    @POST("user/logIn")
    Call<User> loginUser(@Body User user);

    @GET ("user/{userName}")
    Call<User> getUserbyUserName(@Path("userName") String userName);

    @PUT("user/updateUser/{oldUsername}/{newUsername}")
    Call<User> updateUserName(@Path("oldUsername") String oldUsername, @Path("newUsername") String newUsername);

    @PUT("user/updatePassword")
    Call<User> updatePassword(@Body PasswordUpdate password);

    @DELETE("user/delete/{userID}")
    Call<User> deleteUser(@Path("userID") String userID);
}