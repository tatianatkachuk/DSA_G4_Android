package com.example.android_whostolesantasbeard.entities;

public class Item {
   private String id;
   private String name;
   private String description;
   private int price;


   public String getId() {
      return id;
   }
   public String getName() {
      return name;
   }
   public int getPrice(){return price;}
   public String getDescription(){return description;}

   public void setId(String id) {this.id = id;}
   public void setName(String name) {this.name = name;}
   public void setPrice(int price) {
      this.price = price;
   }
   public void setDescription(String description){
      this.description = description;
   }

   public Item(String id, String name, String description, int price) {
      this.name = name;
      this.description = description;
      this.price = price;
      this.id = id;
   }

   @Override
   public String toString() {
      return "User{" +
              "id='" + id + '\'' +
              "description='" + description + '\'' +
              ", price='" + price + '\'' +
              '}';
   }
   public Item(){ }

}
