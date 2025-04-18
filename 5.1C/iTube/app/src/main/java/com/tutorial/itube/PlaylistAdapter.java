package com.tutorial.itube;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder> {
    private List<String> videoUrls;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(String videoUrl);
    }

    public PlaylistAdapter(List<String> videoUrls, OnItemClickListener listener) {
        this.videoUrls = videoUrls;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_playlist, parent, false);
        return new PlaylistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistViewHolder holder, int position) {
        String videoUrl = videoUrls.get(position);
        holder.videoUrlTextView.setText(videoUrl);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(videoUrl);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoUrls.size();
    }

    static class PlaylistViewHolder extends RecyclerView.ViewHolder {
        TextView videoUrlTextView;

        public PlaylistViewHolder(@NonNull View itemView) {
            super(itemView);
            videoUrlTextView = itemView.findViewById(R.id.video_url_textview);
        }
    }
}