package com.example.android_whostolesantasbeard.entities;

import java.util.List;

public class ItemList {
    private int size;
    private List<Item> itemlist;

    public List<Item> getItems(){return itemlist;}

    public void setItems(List<Item> itemlist) {
        this.itemlist = itemlist;
    }

    public Item getItem(int itemIndex){
        return itemlist.get(itemIndex);
    }

}
