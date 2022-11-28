package com.example.android_whostolesantasbeard;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_whostolesantasbeard.entities.ShopItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShopItems extends RecyclerView.Adapter<ShopItems.ShopItemView>{

    ArrayList<ShopItem> listShopItems;
    String id;
    IWSSBService service;
    // Inventory inventory=new Inventory(); Inventario actual
    int cash;
    User user= new User();

    public ShopItems(ArrayList<ShopItem> listShopItems, String id, int cash) {
        this.listShopItems = listShopItems;
        this.id=id;
        this.cash=cash;

        service = APIClient.getClient().create(IWSSBService.class);
    }

    @NonNull
    @Override
    public ShopItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_store, null, false);
        return new ShopItemView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopItemView holder, int position) {
        holder.name.setText(listShopItems.get(position).getName());
        holder.desc.setText(listShopItems.get(position).getDescription());
        holder.price.setText(listShopItems.get(position).getPrice()); 

    }

    public class ShopItemView extends RecyclerView.ViewHolder {
        TextView name, desc, price;
        Button buy;

        public ShopItemView(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.productName);
            desc = (TextView) itemView.findViewById(R.id.productDescription);
            price = (TextView) itemView.findViewById(R.id.productPrice);
            buy = (Button) itemView.findViewById(R.id.buttonToBuy);

        }
    }

    @Override
    public int getItemCount() {
        return listShopItems.size();
    }

}
