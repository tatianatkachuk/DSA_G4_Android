package com.example.android_whostolesantasbeard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_whostolesantasbeard.entities.Item;


import java.util.ArrayList;
import java.util.List;

public class ItemsStoreRecyclerViewAdapter extends RecyclerView.Adapter<ItemsStoreRecyclerViewAdapter.ShopItemView>{

    List<Item> listShopItems;
    String id;
    IWSSBService service;
    // Inventory inventory=new Inventory(); Inventario actual
    int cash;
    User user= new User();

    public ItemsStoreRecyclerViewAdapter(ArrayList<Item> listShopItems) {
        this.listShopItems = listShopItems;
        service = APIClient.getClient().create(IWSSBService.class);
    }

    @NonNull
    @Override
    public ShopItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_store_row, null, false);
        return new ShopItemView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopItemView holder, int position) {
        holder.name.setText(listShopItems.get(position).getName());
        holder.desc.setText(listShopItems.get(position).getDescription());
        holder.price.setText(listShopItems.get(position).getPrice());
        /*GlideApp.with(holder.image.getContext())
                .load(listShopItems.get(position).getImage())
                .into(holder.image);*/
    }

    public class ShopItemView extends RecyclerView.ViewHolder {
        TextView name, desc, price;
        ImageView image;
        Button buy;

        public ShopItemView(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.productName);
            desc = (TextView) itemView.findViewById(R.id.productDescription);
            price = (TextView) itemView.findViewById(R.id.productPrice);
            image =(ImageView) itemView.findViewById(R.id.productImage);
            buy = (Button) itemView.findViewById(R.id.buttonToBuy);

        }
    }

    @Override
    public int getItemCount() {
        return listShopItems.size();
    }

    public void setData(List<Item> myDataset) {
        listShopItems = myDataset;
        notifyDataSetChanged();
    }

}
