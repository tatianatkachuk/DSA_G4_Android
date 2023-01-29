package com.example.android_whostolesantasbeard;

import com.example.android_whostolesantasbeard.entities.InventoryElement;
import com.example.android_whostolesantasbeard.entities.Item;
import com.example.android_whostolesantasbeard.entities.LeaderboardEntry;
import com.example.android_whostolesantasbeard.entities.PasswordUpdate;
import com.example.android_whostolesantasbeard.entities.Query;
import com.example.android_whostolesantasbeard.entities.UserCredentials;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IWSSBService {
    @POST("user/signUp")
    Call<UserCredentials> registerAccount(@Body UserCredentials credentials);

    @POST("user/logIn")
    Call<User> loginUser(@Body User user);

    @POST("technicalService/report")
    Call<Issue> reportIssue(@Body Issue issueInfo);

    @GET ("user/{userName}")
    Call<User> getUserbyUserName(@Path("userName") String userName);

    @PUT("user/updateUser/{oldUsername}/{newUsername}")
    Call<User> updateUserName(@Path("oldUsername") String oldUsername, @Path("newUsername") String newUsername);

    @PUT("user/updatePassword")
    Call<User> updatePassword(@Body PasswordUpdate password);

    @DELETE("user/delete/{userID}")
    Call<User> deleteUser(@Path("userID") String userID);

    @PUT("user/updateImage/{id}/{newImage}")
    Call<User> updateProfileImage(@Path("id") String userID, @Path("newImage") Integer newImage);

    @POST("technicalService/question")
    Call<Query> askQuery(@Body Query query);

    @GET("item/itemsList")
    Call <ArrayList<Item>> getStoreItems();

    @PUT("item/PurchaseItem/{ItemName}/{UserName}")
    Call<Boolean> TryPurchase(@Path("ItemName") String itemName, @Path("UserName") String username);

    @GET("match/ranking")
    Call<ArrayList<LeaderboardEntry>> getLeaderBoard();

    @GET("item/inventory/{UserId}")
    Call <ArrayList<InventoryElement>> getInventory(@Path("UserId") String userId);

}