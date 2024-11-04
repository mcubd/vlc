package com.a.acs2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.DisplayMetrics;
public class sub_adapter extends RecyclerView.Adapter<MyViewHolder> {


    Context context;
    List<Item> items;
    SelectListener listener;



    public sub_adapter(Context context, List<Item> items,SelectListener listener) {
        this.context = context;
        this.items = items;
        this.listener=listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.sub,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull  MyViewHolder holder, int position) {
        holder.nameView.setText(items.get(position).getName());
        holder.emailView.setText(items.get(position).getEmail());
        holder.imageView0.setImageResource(items.get(position).getImage());


       // holder.nameView.setVisibility(View.INVISIBLE);



       int displayWidth = holder.itemView.getContext().getResources().getDisplayMetrics().widthPixels;

        int newWidth = (int) (displayWidth * 0.8); // 45% of display width

        // Set the new width while preserving aspect ratio
        ViewGroup.LayoutParams params = holder.imageView0.getLayoutParams();
        params.width = newWidth;
          params.height = (int) (displayWidth * 0.55); // This will auto-adjust the height based on the aspect ratio
        holder.imageView0.setLayoutParams(params);

         holder.imageView0.setShapeAppearanceModel(
                 holder.imageView0.getShapeAppearanceModel()
                 .toBuilder()
                 .setAllCornerSizes(22f) // Set radius in pixels or dp
                 .build()
         );







        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onIteamClicked(items.get(position), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

