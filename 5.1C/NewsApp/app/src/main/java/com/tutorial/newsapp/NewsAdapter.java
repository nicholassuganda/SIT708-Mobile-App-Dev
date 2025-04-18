package com.tutorial.newsapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private List<NewsItem> newsItems;
    private boolean isHorizontal;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(NewsItem item);
    }

    public NewsAdapter(List<NewsItem> newsItems, boolean isHorizontal, OnItemClickListener listener) {
        this.newsItems = newsItems;
        this.isHorizontal = isHorizontal;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(isHorizontal ? R.layout.item_news_horizontal : R.layout.item_news_vertical, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        NewsItem item = newsItems.get(position);
        holder.bind(item, listener);
    }

    @Override
    public int getItemCount() {
        return newsItems.size();
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleTextView;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.news_image);
            titleTextView = itemView.findViewById(R.id.news_title);
        }

        public void bind(final NewsItem newsItem, final OnItemClickListener listener) {
            titleTextView.setText(newsItem.getTitle());
            Glide.with(itemView.getContext())
                    .load(newsItem.getImageUrl())
                    .into(imageView);

            itemView.setOnClickListener(v -> listener.onItemClick(newsItem));
        }
    }
}
