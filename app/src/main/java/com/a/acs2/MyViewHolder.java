package com.a.acs2;


import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    ImageView pauseView ,playView,downView;
    ImageView dltView;
    ImageView doneView;
    com.google.android.material.imageview.ShapeableImageView imageView0;
    TextView nameView,emailView,percentView,percent0View,imgnumView,lecTitle;
    RelativeLayout relativeLayout,relativeLayout22;
    LinearLayout linearLayout;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageview);
        imageView0 = itemView.findViewById(R.id.imageview0);
        nameView = itemView.findViewById(R.id.name);
        lecTitle = itemView.findViewById(R.id.lecTitle);
        emailView = itemView.findViewById(R.id.email);
//        linearLayout = itemView.findViewById(R.id.lin);
        relativeLayout = itemView.findViewById(R.id.parent);
        relativeLayout22 = itemView.findViewById(R.id.re);
        //linearLayout = itemView.findViewById(R.id.parent0);
        pauseView = itemView.findViewById(R.id.pause);
        downView = itemView.findViewById(R.id.down);
        dltView = itemView.findViewById(R.id.dlt);
        doneView = itemView.findViewById(R.id.done);
        percentView = itemView.findViewById(R.id.percent);
        percent0View = itemView.findViewById(R.id.percent0);
        imgnumView = itemView.findViewById(R.id.num);



    }
}
