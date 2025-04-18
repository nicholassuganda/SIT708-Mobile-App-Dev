package com.tutorial.newsapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class NewsDetailFragment extends Fragment {
    private NewsItem newsItem;

    public NewsDetailFragment() {
    }

    public static NewsDetailFragment newInstance(NewsItem newsItem) {
        NewsDetailFragment fragment = new NewsDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable("newsItem", newsItem);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            newsItem = (NewsItem) getArguments().getSerializable("newsItem");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_detail, container, false);

        ImageView newsImage = view.findViewById(R.id.news_detail_image);
        TextView newsTitle = view.findViewById(R.id.news_detail_title);
        TextView newsDescription = view.findViewById(R.id.news_detail_description);
        RecyclerView relatedNewsRecyclerView = view.findViewById(R.id.relatedNewsRecyclerView);

        // Set news details
        Glide.with(this).load(newsItem.getImageUrl()).into(newsImage);
        newsTitle.setText(newsItem.getTitle());
        newsDescription.setText(newsItem.getDescription());

        // Sample related news
        List<NewsItem> relatedNews = new ArrayList<>();
        relatedNews.add(new NewsItem("Related News 1", "Description 1", R.drawable.man, false));
        relatedNews.add(new NewsItem("Related News 2", "Description 2", R.drawable.tree, false));

        // Setup Related News RecyclerView
        relatedNewsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        relatedNewsRecyclerView.setAdapter(new NewsAdapter(relatedNews, false, item -> {
            // Handle related news click - replace with new detail
            ((MainActivity)requireActivity()).showNewsDetail(item);
        }));

        return view;
    }
}