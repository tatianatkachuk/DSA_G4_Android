package com.example.android_whostolesantasbeard.entities;

public class InventoryElement {
    String id;
    String description;
    String name;
    int image;
    int damage;
    int heal;

    public InventoryElement(String id, String description, String name, int image, int damage, int heal) {
        this.id = id;
        this.description = description;
        this.name = name;
        this.image = image;
        this.damage = damage;
        this.heal = heal;
    }

    public InventoryElement(){}

    public String getDescription() { return description;}

    public String getId() {return id;}

    public String getName() {return name;}

    public int getImage() {return image;}

    public int getDamage() {return damage;}

    public int getHeal() {return heal;}

}
