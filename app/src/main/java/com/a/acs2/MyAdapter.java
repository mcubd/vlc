package com.a.acs2;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {


    Context context;
    List<Item> items;
    SelectListener listener;

    public MyAdapter(Context context, List<Item> items,SelectListener listener) {
        this.context = context;
        this.items = items;
        this.listener=listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull  MyViewHolder holder, int position) {
        holder.nameView.setText(items.get(position).getName());
        holder.emailView.setText(items.get(position).getEmail());
        holder.imageView.setImageResource(items.get(position).getImage());

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onIteamClicked(items.get(position),  position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void removeItem() {
        items.remove(5);  // Remove the item from the data source
        notifyItemRemoved(5); // Notify the adapter about the removed item
        notifyItemRangeChanged(5, items.size()); // Notify for the changed range
    }
}
