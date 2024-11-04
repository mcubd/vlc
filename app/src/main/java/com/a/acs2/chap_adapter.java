package com.a.acs2;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class chap_adapter extends RecyclerView.Adapter<MyViewHolder> {


    Context context;
    List<Item> items;
    SelectListener listener;



    public chap_adapter(Context context, List<Item> items, SelectListener listener) {
        this.context = context;
        this.items = items;
        this.listener=listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.chap_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull  MyViewHolder holder, int position) {
        holder.nameView.setText(items.get(position).getName());
        holder.emailView.setText(items.get(position).getEmail());
        holder.imageView0.setImageResource(items.get(position).getImage());


       // holder.nameView.setVisibility(View.INVISIBLE);



        int displayWidth = holder.itemView.getContext().getResources().getDisplayMetrics().widthPixels;
        int dpp =(int) holder.itemView.getContext().getResources().getDisplayMetrics().density;

//        Log.d("ffffffff",dpp+"  "+displayWidth);
        int newWidth = (int) (displayWidth * 0.9);




        ViewGroup.LayoutParams params0 = holder.relativeLayout22.getLayoutParams();
        params0.width = newWidth;
          params0.height = (int) (displayWidth * 0.59);
          holder.relativeLayout22.setLayoutParams(params0);


        ViewGroup.LayoutParams params = holder.imageView0.getLayoutParams();
        params.width =  (int) (displayWidth * 0.78);
        params.height = (int) (displayWidth * 0.43);
        holder.imageView0.setLayoutParams(params);



        GradientDrawable roundedDrawable = new GradientDrawable();
        roundedDrawable.setColor(0xFFFFFFFF); // Background color (white)
        roundedDrawable.setCornerRadius(16 * dpp); // Corner radius in pixels
        holder.relativeLayout22.setBackground(roundedDrawable);


        holder.imageView0.setShapeAppearanceModel(
                 holder.imageView0.getShapeAppearanceModel()
                 .toBuilder()
                 .setAllCornerSizes(26f) // Set radius in pixels or dp
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

