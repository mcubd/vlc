package com.a.acs2;


import static androidx.fragment.app.FragmentManager.TAG;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class playlist_mini_adapter0 extends RecyclerView.Adapter<MyViewHolder> {


    Context context;
    List<playlist_mini> items;
    SelectListenermini listener;
    String selectState;
    private int clickedPosition = -1; // Keep track of clicked item position
    private int clickedPositionInSelectMode = -1; // Keep track of clicked item position
    private int longClickedPosition = -1; // Keep track of clicked item position
    private int longClickedCount = 0; // Keep track of clicked item position
    int lastBeforeLong = -1;

    public playlist_mini_adapter0(Context context, List<playlist_mini> items, SelectListenermini listener, String selectState) {
        this.context = context;
        this.items = items;
        this.listener = listener;
        this.selectState = selectState;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.playlist_mini, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nameView.setText(items.get(position).getName());
//        holder.emailView.setText(items.get(position).getEmail());
        holder.imageView.setImageResource(items.get(position).getImage());
        holder.doneView.setImageResource(R.drawable.done);
        holder.pauseView.setImageResource(items.get(position).getPauseImg0());


        holder.pauseView.setVisibility(View.GONE);
        holder.doneView.setVisibility(View.GONE);
        holder.percent0View.setVisibility(View.GONE);


        String email = items.get(position).getEmail();

        if (selectState.contains("iiii")) {


        } else {


            if (items.get(position).getpercenOnly() == 100) {

                holder.doneView.setVisibility(View.INVISIBLE);
                holder.percent0View.setVisibility(View.VISIBLE);
                holder.percent0View.setText(items.get(position).getPercent0());



            } else if (items.get(position).getPercent0().equals("down")) {


                holder.doneView.setVisibility(View.GONE);
                holder.percent0View.setVisibility(View.GONE);
            } else {


                holder.doneView.setVisibility(View.GONE);
                holder.percent0View.setVisibility(View.VISIBLE);
                holder.percent0View.setText(items.get(position).getPercent0());


            }
        }


        //--------------------------------------------set-Color-Start---------------------------------------------------------
        //--------------------------------------------set-Color-Start---------------------------------------------------------
        //--------------------------------------------set-Color-Start---------------------------------------------------------


        String clr0 = items.get(position).getClr();
        Log.d(TAG, "-------clickedPositionInSelectMode---------k" + clickedPositionInSelectMode + "  " + clr0);


        if (selectState.contains("select")) {
            String clr = items.get(position).getClr();

            if (position == longClickedPosition && longClickedCount == 1) {

                longClickedCount = longClickedCount + 1;
                holder.relativeLayout.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.green2));
                items.get(position).setClr("green2");
                Log.d(TAG, "-----0-- " + position);

            } else if (position == clickedPositionInSelectMode && clr.contains("green2")) {

                holder.relativeLayout.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.title_bg));
                items.get(position).setClr("title_bg");
                Log.d(TAG, "----1--- " + position);

            } else if (position == clickedPositionInSelectMode && clr.contains("download")) {

                holder.relativeLayout.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.green2));
                items.get(position).setClr("green2");
                Log.d(TAG, "----2--- " + position);

            } else if (position == clickedPositionInSelectMode && clr.contains("title_bg")) {

                holder.relativeLayout.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.green2));
                items.get(position).setClr("green2");
                Log.d(TAG, "---3---- " + position);

            } else {

                if (clr.contains("title_bg")) {
                    holder.relativeLayout.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.title_bg));
                    items.get(position).setClr("title_bg");
                    Log.d(TAG, "---4---- " + position);
                } else if (clr.contains("green2")) {
                    holder.relativeLayout.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.green2));
                    items.get(position).setClr("green2");
                    Log.d(TAG, "---5---- " + position);
                } else if (clr.contains("download")) {
                    holder.relativeLayout.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.download));
                    items.get(position).setClr("download");
                    Log.d(TAG, "---5.2---- " + position);
                } else {
                    Log.d(TAG, "---6---- " + position);
                }

            }

        } else if (position == clickedPosition) {

            clickedPositionInSelectMode = -1;
            longClickedCount = 0;
            Log.d(TAG, "--Normal-1---- " + position);
            holder.relativeLayout.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.download));
            items.get(position).setClr("download");


        } else if (clickedPosition == -1) {

            Log.d(TAG, "--Normal-2---- " + position);
            clickedPositionInSelectMode = -1;
            longClickedCount = 0;

            if (items.get(position).getActive().contains("active")) {
                holder.relativeLayout.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.download));
                items.get(position).setClr("download");
            } else {
                holder.relativeLayout.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.title_bg));
                items.get(position).setClr("title_bg");
            }


        } else {

            clickedPositionInSelectMode = -1;
            longClickedCount = 0;
            Log.d(TAG, "--Normal-3---- " + position);
            holder.relativeLayout.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.title_bg));
            items.get(position).setClr("title_bg");
        }


        //------------------------------------set-Color-End-------------------------------------------------------------------------
        //------------------------------------set-Color-End-------------------------------------------------------------------------
        //------------------------------------set-Color-End-------------------------------------------------------------------------
        //------------------------------------set-Color-End-------------------------------------------------------------------------
        //------------------------------------set-Color-End-------------------------------------------------------------------------


        holder.pauseView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                notifyDataSetChanged();
                listener.onPauseClicked(items.get(position), position);

            }
        });


        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (selectState.contains("select")) {
                    clickedPositionInSelectMode = position;
                } else {
                    clickedPosition = position;
                }

                notifyDataSetChanged();

                listener.onIteamClicked(items.get(position));

            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                longClickedPosition = position;
                longClickedCount = longClickedCount + 1;
                notifyDataSetChanged();

                listener.onLongClicked(items.get(position), position);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public void updateDownloadPercentage(int position, String text, int percentage) {

        playlist_mini item = items.get(position);
        item.setPercent0(text);
        item.setpercenOnly(percentage);

        notifyItemChanged(position, text);

    }

    public void updateDownloadPercentage0(int position, String text, int percentage) {

        playlist_mini item = items.get(position);
        item.setPercent0(text);
        item.setpercenOnly(percentage);

        notifyItemChanged(position, text);

    }

    public void refresh(int position) {
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

    public String getActiveItem() {

//        for (int i = 0; i < items.size(); i++) {
        if (clickedPosition > -1) {
            return String.valueOf(items.get(clickedPosition).percenOnly);
        }
//        }
        return "notClicked";
    }

    public String getActiveItemId() {

//        for (int i = 0; i < items.size(); i++) {
        if (clickedPosition > -1) {
            return String.valueOf(items.get(clickedPosition).getVidId());
        }
//        }
        return "notClicked";
    }

    public int getActiveItemPosi() {

        return clickedPosition;
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


    public void updateItemPercentAndState(int position, int percent, String state) {

        items.get(position).setpercenOnly(percent);
        items.get(position).setMsg(state);

        // Notify the adapter that the item at 'position' has changed
        notifyItemChanged(position);
    }

    public void updateItemPercent0AndPerent(int position, String percent0, int percent) {

        items.get(position).setpercenOnly(percent);
        items.get(position).setPercent0(percent0);

        // Notify the adapter that the item at 'position' has changed
        notifyDataSetChanged();
    }


    public void updateSelectState(String selectState) {
        this.selectState = selectState;
        notifyDataSetChanged(); // Refresh the RecyclerView if needed
    }

    public void updateBg(int bg, int position) {

        items.get(position).setBackgroundColor(bg);
        notifyItemChanged(position, bg); // Refresh the RecyclerView if needed
    }

    public String lastClickedUrl() {

        if (clickedPosition > -1) {
            return items.get(clickedPosition).getUrl();
        }
        return "src";


    }


}
