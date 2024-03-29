package com.example.android_whostolesantasbeard;


import android.app.Application;
import android.content.Context;
import android.content.res.loader.AssetsProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.res.Resources;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_whostolesantasbeard.entities.Item;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoreRecyclerViewAdapter extends RecyclerView.Adapter<StoreRecyclerViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<Item> itemsToBeDisplayed;
    IWSSBService service;

    public StoreRecyclerViewAdapter(Context context, ArrayList<Item> itemsList){
        this.context = context;
        itemsToBeDisplayed = itemsList;
        service = APIClient.getClient().create(IWSSBService.class);
    }
    private void showToast(String message){
        Toast.makeText(this.context, message, Toast.LENGTH_LONG).show();
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
        holder.itemID = currentItem.getId();
        String imageName = currentItem.getImage();
        int drawableId = this.context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
        holder.itemImage.setImageResource(drawableId);
        // Set image here
    }

    @Override
    public int getItemCount() {
        return itemsToBeDisplayed.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        String itemID;
        ImageView itemImage;
        TextView itemName;
        TextView itemDescription;
        TextView itemPrice;
        Button itemBuyAttempt;
        IWSSBService apiService;
        private void showToast(String message, Context context){
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.item_image);
            itemName = (TextView) itemView.findViewById(R.id.item_name);
            itemPrice = (TextView)itemView.findViewById(R.id.item_price);
            itemDescription = (TextView)itemView.findViewById(R.id.item_description);
            itemBuyAttempt = (Button) itemView.findViewById(R.id.store_buyButton);
            itemBuyAttempt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Call<Boolean> call = apiService.TryPurchase(itemID, Main.S_MyUsername);
                    call.enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            int responseCode = response.code();
                            switch (responseCode){
                                case 200 :{
                                    showToast("You purchased correctly this item! Thank you!",view.getContext());
                                    break;
                                }
                                case 409 : {
                                    showToast("You already have this item! Why would you need two of them?",view.getContext());
                                    break;
                                }
                                case 402 : {
                                    showToast("You don't have enough money to buy this, ask Santa for money!",view.getContext());
                                    break;
                                }
                                default: {
                                    break;
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {
                            Log.d("ERRRO", "This should not happen at all");
                        }
                    });
                }
            });
        }
    }
}
