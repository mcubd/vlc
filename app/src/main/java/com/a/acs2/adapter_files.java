package com.a.acs2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.List;

public class adapter_files extends RecyclerView.Adapter<adapter_files.viewholder> {

    private final List<model_files> fileList;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(model_files item) throws IOException;
    }

    public adapter_files(List<model_files> fileList, OnItemClickListener listener) {
        this.fileList = fileList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_files, parent, false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        model_files item = fileList.get(position);
        holder.bind(item, listener);
    }

    @Override
    public int getItemCount() {
        return fileList.size();
    }

    public static class viewholder extends RecyclerView.ViewHolder {
        private final ImageView icon;
        private final TextView title;
        private final TextView file_size;

        public viewholder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon);
            title = itemView.findViewById(R.id.title);
            file_size = itemView.findViewById(R.id.file_size);
        }

        public void bind(model_files item, OnItemClickListener listener) {
            title.setText(item.getName());

            if (item.isDirectory()) {
                icon.setImageResource(R.drawable.ic_folder);
                file_size.setVisibility(View.GONE);
            } else {
                icon.setImageResource(R.drawable.ic_file);
                file_size.setText(item.getFormattedSize());
                file_size.setVisibility(View.VISIBLE);
            }

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    try {
                        listener.onItemClick(item);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
    }
}