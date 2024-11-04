package com.a.acs2;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class lec_adapter extends RecyclerView.Adapter<MyViewHolder> {


    Context context;
    List<Item> items;
    SelectListener listener;


    public lec_adapter(Context context, List<Item> items, SelectListener listener) {
        this.context = context;
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.lec_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        holder.nameView.setText(items.get(position).getName());
        holder.emailView.setText(items.get(position).getEmail());
        holder.imageView0.setImageResource(items.get(position).getImage());


        // holder.nameView.setVisibility(View.INVISIBLE);


        int displayWidth = holder.itemView.getContext().getResources().getDisplayMetrics().widthPixels;


        ViewGroup.LayoutParams params = holder.imageView0.getLayoutParams();
        params.width = (int) (displayWidth * 0.8);
        params.height = (int) (displayWidth * 0.49);
        holder.imageView0.setLayoutParams(params);

        ViewGroup.LayoutParams params2 = holder.emailView.getLayoutParams();
        params2.width = (int) (displayWidth * 0.8);
        params2.height = (int) (displayWidth * 0.2);
        holder.emailView.setLayoutParams(params2);

//        GradientDrawable roundedDrawable = new GradientDrawable();
//        roundedDrawable.setColor(0xFFFFFFFF); // Background color (white)
//        roundedDrawable.setCornerRadius(16 * dpp); // Corner radius in pixels
//        holder.relativeLayout22.setBackground(roundedDrawable);


//        holder.imageView0.setShapeAppearanceModel(
//                 holder.imageView0.getShapeAppearanceModel()
//                 .toBuilder()
//                 .setAllCornerSizes(26f) // Set radius in pixels or dp
//                 .build()
//         );


        GradientDrawable roundedBackground3 = new GradientDrawable();
        roundedBackground3.setColor(Color.WHITE); // Background color
        roundedBackground3.setCornerRadii(new float[]{0, 0, 0, 0, 16, 16, 16, 16}); // Only round bottom corners
        holder.emailView.setBackground(roundedBackground3);


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

