package com.a.acs2;







import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.RelativeLayout;

public class dmanager_adapter extends RecyclerView.Adapter<MyViewHolder> {


    Context context;
    List<ItemDmanager> items;
    SelectListenerdownload listener;
    private int clickedPosition = -1; // Keep track of clicked item position
    private int visiblePosition = -1;

    public dmanager_adapter(Context context, List<ItemDmanager> items, SelectListenerdownload listener) {
        this.context = context;
        this.items = items;
        this.listener=listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.downloads,parent,false));
    }

    public void removeItem(int position) {

        items.remove(position);  // Remove the item from the data source
        notifyItemRemoved(position); // Notify the adapter about the removed item
        notifyItemRangeChanged(position, items.size()); // Notify for the changed range
        notifyItemChanged(position+1);

    }
//    public void removeItem(int position) {
//
//        if (items.get(position).getHidden()) {
//            items.get(position).setHidden(true);
//            setTextHidden(!isVisible);
//            holder.doneView.setVisibility(View.VISIBLE); // Show the image
//            holder.emailView.setVisibility(View.GONE); // Show the image
//            holder.pauseView.setVisibility(View.INVISIBLE); // Show the image
//        }
//
//        notifyItemChanged(position); // Notify the adapter about the removed item
//     }

    @Override
    public void onBindViewHolder(@NonNull  MyViewHolder holder, int position) {
        holder.nameView.setText(items.get(position).getName());
        holder.emailView.setText(items.get(position).getEmail());
        holder.lecTitle.setText(items.get(position).getLectitle());
        holder.imageView.setImageResource(items.get(position).getImage());
        holder.dltView.setImageResource(items.get(position).getDlt());
        holder.pauseView.setImageResource(items.get(position).getPause());
        holder.doneView.setImageResource(R.drawable.done);
        holder.imgnumView.setText(items.get(position).getimgnum());
   holder.imgnumView.setVisibility(View.GONE); // Show the image

//        System.out.println("adapter-why?-----"+items.get(position).getPercent()+"--"+position  );

//        if (items.get(position).getPercent()  == 100) {
//            System.out.println("100%------" + +items.get(position).getPercent()+"--"+position  );
//            holder.doneView.setVisibility(View.VISIBLE); // Show the image
//            holder.emailView.setVisibility(View.GONE); // Show the image
//            holder.pauseView.setVisibility(View.INVISIBLE); // Show the image
//        }

        if (items.get(position).getHidden()) {

            holder.doneView.setVisibility(View.GONE); // Show the image
            holder.emailView.setVisibility(View.VISIBLE); // Show the image
            holder.pauseView.setVisibility(View.INVISIBLE); // Show the image
        } else {
            holder.doneView.setVisibility(View.GONE); // Show the image
            holder.emailView.setVisibility(View.VISIBLE); // Show the image
            holder.pauseView.setVisibility(View.VISIBLE); // Show the image
        }

        if (items.get(position).getPercent() ==100) {
              holder.pauseView.setVisibility(View.INVISIBLE); // Show the image

        }





        String email = items.get(position).getEmail();

        holder.pauseView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickedPosition = position;
                notifyDataSetChanged();
                listener.onPauseClicked(items.get(position),position);

            }
        });

        holder.dltView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickedPosition = position;
                notifyDataSetChanged();
                listener.onDltClicked(items.get(position),position);

            }
        });





        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                clickedPosition = position;
                notifyDataSetChanged();


                listener.onIteamClicked(items.get(position),position);

            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                // Do your stuff
                return false;
            }
        });
    }




    @Override
    public int getItemCount() {
        return items.size();
    }


    // Method to update only the emailView with the download percentage
    public void updateDownloadPercentage(int position, String text,float percentage ) {
        if (position >= 0 && position < items.size()) {
            ItemDmanager item = items.get(position);
            item.setEmail(text);
            item.setPercent(percentage);

            // Notify adapter to update only this specific item's emailView
            notifyItemChanged(position, text);
        }
    }

    public void updatePauseImg(int position, int pic) {
        if (position >= 0 && position < items.size()) {
            ItemDmanager item = items.get(position);
//            item.setEmail(percentage);
            item.setPause(pic);
            System.out.println("pause lil"  );

            // Notify adapter to update only this specific item's emailView
            notifyItemChanged(position, pic);
        }
    }


    public int getPositionByName(String name) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getimgnum().equals(name)) {
                return i; // Return the position if found
            }
        }
        return -1; // Return -1 if not found
    }




//    public void done(int position) {
//        if (position >= 0 && position < items.size()) {
//            Item item = items.get(position);
//            item.setPause(R.drawable.done);
//            notifyItemChanged(position, R.drawable.done);
//        }
//    }


}

