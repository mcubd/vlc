package com.a.acs2;

import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    private final ArrayList<Video> videos;
    private final VideoClickListener listener;

    public VideoAdapter(ArrayList<Video> videos, VideoClickListener listener) {
        this.videos = videos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Video video = videos.get(position);
        holder.bind(video);
        holder.itemView.setOnClickListener(v -> listener.onClick(video));
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView thumbnail;

        ViewHolder(View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.thumbnail);
        }

        void bind(Video video) {
            Bitmap thumb = MediaStore.Video.Thumbnails.getThumbnail(
                    itemView.getContext().getContentResolver(),
                    video.id,
                    MediaStore.Video.Thumbnails.MINI_KIND,
                    null
            );
            thumbnail.setImageBitmap(thumb);
        }
    }

    interface VideoClickListener {
        void onClick(Video video);
    }
}