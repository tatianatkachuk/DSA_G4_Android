package com.example.android_whostolesantasbeard;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_whostolesantasbeard.entities.InventoryElement;

import java.util.ArrayList;
public class InventoryRecyclerViewAdapter extends RecyclerView.Adapter<InventoryRecyclerViewAdapter.MyViewHolder> {
    Context context;
    ArrayList<InventoryElement> inventoryL;
    public InventoryRecyclerViewAdapter(ArrayList<InventoryElement> inventoryL, Context context){
        this.inventoryL = inventoryL;
        this.context = context;
    }
    @NonNull
    @Override
    public InventoryRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.inventory_recycler_view_row, parent,false);
        return new InventoryRecyclerViewAdapter.MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull InventoryRecyclerViewAdapter.MyViewHolder holder, int position) {
        InventoryElement currentItem = inventoryL.get(position);
        holder.itemName.setText(currentItem.getName());
        holder.descriptionVal.setText(currentItem.getDescription());
        String imageName = currentItem.getImage();
        int drawableId = this.context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
        holder.itemImage.setImageResource(drawableId);

    }
    @Override
    public int getItemCount() {
        return inventoryL.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView itemName;
        TextView descriptionVal;
        ImageView itemImage;
        public MyViewHolder(View itemView) {
            super(itemView);
            itemName = (TextView) itemView.findViewById(R.id.inventory_itemName);
            descriptionVal = (TextView) itemView.findViewById(R.id.inventory_description);
            itemImage = (ImageView) itemView.findViewById(R.id.inventory_image);
        }
    }
}