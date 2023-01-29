package com.example.android_whostolesantasbeard;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        View view = inflater.inflate(R.layout.recycler_inventory_row, parent,false);
        return new InventoryRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InventoryRecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.itemName.setText(inventoryL.get(position).getName());
        holder.descriptionVal.setText(inventoryL.get(position).getDescription());
        //holder.healVal.setText(inventoryL.get(position).getHeal());
        //holder.damageVal.setText(inventoryL.get(position).getDamage());
        // Image
    }

    @Override
    public int getItemCount() {
        return inventoryL.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView itemName;
        TextView descriptionVal;
        //TextView healVal;
        //TextView damageVal;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemName = (TextView) itemView.findViewById(R.id.itemNameVal);
            descriptionVal = (TextView) itemView.findViewById(R.id.descriptionVal);
            //healVal = (TextView) itemView.findViewById(R.id.healVal);
            //damageVal = (TextView) itemView.findViewById(R.id.damageVal);
        }
    }
}
