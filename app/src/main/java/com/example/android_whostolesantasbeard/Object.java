package com.example.android_whostolesantasbeard;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Object {
   private String id;
   private String description;
   private int price;
   public String getId() {
      return id;
   }
   public int getPrice(){return price;}
   public String getDescription(){return description;}

   public void setId(String id) {

      this.id = id;
   }
   public void setPrice(int price) {
      this.price = price;
   }
   public void setDescription(String description){
      this.description = description;
   }
   @Override
   public String toString() {
      return "User{" +
              "id='" + id + '\'' +
              "description='" + description + '\'' +
              ", price='" + price + '\'' +
              '}';
   }
}
