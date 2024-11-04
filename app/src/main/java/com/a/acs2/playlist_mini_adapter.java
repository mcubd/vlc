package com.a.acs2;







import static androidx.fragment.app.FragmentManager.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class playlist_mini_adapter extends RecyclerView.Adapter<MyViewHolder> {


    Context context;
    List<playlist_mini> items;
    SelectListenermini listener;
    String selectState;
    private int clickedPosition = -1; // Keep track of clicked item position
    private int longClickedPosition = -1; // Keep track of clicked item position

    public playlist_mini_adapter(Context context, List<playlist_mini> items, SelectListenermini listener,String selectState) {
        this.context = context;
        this.items = items;
        this.listener=listener;
        this.selectState=selectState;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.playlist_mini,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull  MyViewHolder holder, int position) {
        holder.nameView.setText(items.get(position).getName());
       // holder.lecTitle.setText(items.get(position).getLectitle());
//        holder.emailView.setText(items.get(position).getEmail());
        holder.imageView.setImageResource(items.get(position).getImage());
        holder.doneView.setImageResource(R.drawable.done);
        holder.pauseView.setImageResource(items.get(position).getPauseImg0());
        holder.downView.setImageResource(R.drawable.download);
        holder.downView.setVisibility(View.GONE);



//        holder.pauseView.setVisibility(View.VISIBLE);


        String email = items.get(position).getEmail();

        if (selectState.contains("select")) {
            holder.pauseView.setVisibility(View.INVISIBLE);
            holder.downView.setVisibility(View.GONE);
        }else {


        if (items.get(position).getpercenOnly() ==100) {

             holder.percent0View.setText("100%");

            holder.doneView.setVisibility(View.INVISIBLE);
            holder.pauseView.setVisibility(View.INVISIBLE);
            holder.downView.setVisibility(View.GONE);
        }
        else if (items.get(position).getPercent0().equals("down")) {
            holder.pauseView.setVisibility(View.INVISIBLE);
            holder.downView.setVisibility(View.VISIBLE);
            holder.doneView.setVisibility(View.GONE);
        }
        else  {
            holder.pauseView.setVisibility(View.VISIBLE);
            holder.downView.setVisibility(View.GONE);
            holder.doneView.setVisibility(View.GONE);

            if (items.get(position).getMsg().equals("state2")) {

                holder.percent0View.setText(items.get(position).getPercent0());
            } else if (items.get(position).getMsg().equals("state1")){

                holder.percent0View.setText(items.get(position).getPercent0());

            }else {
////                holder.pauseView.setVisibility(View.VISIBLE);
                holder.percent0View.setText(items.get(position).getPercent0()+"%%");
            }


        }
        }

        // Set the background color from the item
      //  holder.relativeLayout.setBackgroundColor(context.getResources().getColor(android.R.color.holo_orange_light));


        if (selectState.contains("select")) {
            holder.relativeLayout.setBackgroundColor(items.get(position).getBackgroundColor01());

        }else  {



        // Change the background color based on the clicked position
        if (position == clickedPosition) {
            holder.relativeLayout.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.download));

        } else if (clickedPosition == -1 ){
               holder.relativeLayout.setBackgroundColor(items.get(position).getBackgroundColor01());
        }
        else {
               holder.relativeLayout.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.title_bg));
        }
            }

//        Log.d(TAG, "------------------public--------- " +selectState );



        holder.pauseView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                notifyDataSetChanged();
                listener.onPauseClicked(items.get(position),position);

            }
        });

        holder.downView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickedPosition = position;
                notifyDataSetChanged();
                listener.onDownClicked(items.get(position),position);

            }
        });








        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                clickedPosition = position;
                notifyDataSetChanged();

                listener.onIteamClicked(items.get(position));

            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                longClickedPosition = position;
                notifyDataSetChanged();

                listener.onLongClicked(items.get(position),position);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public void updateDownloadPercentage(int position, String text,int percentage ) {
        if (position >= 0 && position < items.size()) {
            playlist_mini item = items.get(position);
            item.setPercent0(text);
            item.setpercenOnly(percentage);


            // Notify adapter to update only this specific item's emailView
            notifyItemChanged(position, text);
        }
    }

    public void refresh(int position ) {
        if (position >= 0 && position < items.size()) {
            playlist_mini item = items.get(position);

            notifyItemChanged(position);

        }
    }


    public int getPositionByName(String name) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getVidId().equals(name)) {
                return i; // Return the position if found
            }
        }
        return -1; // Return -1 if not found
    }


    public void updatePauseImg(int position, int pic) {
        if (position >= 0 && position < items.size()) {
            playlist_mini item = items.get(position);
//            item.setEmail(percentage);
//            item.setPause(pic);
//            item.setPauseImg(pic);
         //   Log.d(TAG, "ids--------zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz-------- "   );

//            notifyDataSetChanged();
            item.setPauseImg0(pic);



            // Notify adapter to update only this specific item's emailView
            notifyItemChanged(position);
        }
    }


    public void updateItemPercentAndState(int position, int percent,String state) {

        items.get(position).setpercenOnly(percent);
        items.get(position).setMsg(state);

        // Notify the adapter that the item at 'position' has changed
        notifyItemChanged(position);
    }

    public void updateItemPercent0AndPerent(int position, String percent0,int percent) {

        items.get(position).setpercenOnly(percent);
        items.get(position).setPercent0(percent0);

        // Notify the adapter that the item at 'position' has changed
        notifyItemChanged(position);
    }


    public void updateSelectState(String selectState) {
        this.selectState = selectState;
        notifyDataSetChanged(); // Refresh the RecyclerView if needed
    }
    public void updateBg(int bg,int position) {

        items.get(position).setBackgroundColor(bg);
        notifyItemChanged(position,bg); // Refresh the RecyclerView if needed
    }

    public String lastClickedUrl() {

        if (clickedPosition >-1){
            return  items.get(clickedPosition).getUrl();
        }
        return "src";


    }


}
