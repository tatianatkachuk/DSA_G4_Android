package com.example.android_whostolesantasbeard.entities;
public class InventoryElement {
    int damage;

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getHeal() {
        return heal;
    }

    public void setHeal(int heal) {
        this.heal = heal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    String description;
    int heal;
    String id;
    String image;
    String name;
    int price;
    public InventoryElement(int damage, String description, int heal, String id, String image, String name,int price){
        this.damage = damage;
        this.description = description;
        this.heal = heal;
        this.id  = id;
        this.image = image;
        this.name = name;
        this.price = price;
    }
    public InventoryElement(){

    }
}