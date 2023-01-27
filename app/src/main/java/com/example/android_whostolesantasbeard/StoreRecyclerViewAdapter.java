package com.example.android_whostolesantasbeard;

import android.content.Context;
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

public class StoreRecyclerViewAdapter extends RecyclerView.Adapter<StoreRecyclerViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<Item> itemsToBeDisplayed;
    IWSSBService service;

    public StoreRecyclerViewAdapter(Context context, ArrayList<Item> itemsList){
        this.context = context;
        itemsToBeDisplayed = itemsList;
        service = APIClient.getClient().create(IWSSBService.class);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater  = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.store_recycler_view_row,parent,false);
        return new StoreRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Item currentItem = itemsToBeDisplayed.get(position);
        holder.itemName.setText(currentItem.getName());
        holder.itemDescription.setText(currentItem.getDescription());
        holder.itemPrice.setText(currentItem.getPrice() + "coins");
        holder.apiService = service;
    }

    @Override
    public int getItemCount() {
        return itemsToBeDisplayed.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView itemName;
        TextView itemDescription;
        TextView itemPrice;
        Button itemBuyAttempt;
        IWSSBService apiService;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.item_image);
            itemName = (TextView) itemView.findViewById(R.id.item_name);
            itemPrice = (TextView)itemView.findViewById(R.id.item_price);
            itemDescription = (TextView)itemView.findViewById(R.id.item_description);
            itemBuyAttempt =(Button) itemView.findViewById(R.id.store_buyButton);

            itemBuyAttempt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
        }
    }
}
