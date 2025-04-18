package com.tutorial.newsapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView topStoriesRecyclerView;
    private RecyclerView newsRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Sample data
        List<NewsItem> topStories = new ArrayList<>();
        topStories.add(new NewsItem("Breaking News 1", "Description 1", R.drawable.breaking_news, true));
        topStories.add(new NewsItem("Breaking News 2", "Description 2", R.drawable.bull, true));
        topStories.add(new NewsItem("Breaking News 3", "Description 3", R.drawable.man2, true));

        List<NewsItem> newsItems = new ArrayList<>();
        newsItems.add(new NewsItem("News 1", "Description 1", R.drawable.man, false));
        newsItems.add(new NewsItem("News 2", "Description 2", R.drawable.tree, false));
        newsItems.add(new NewsItem("News 3", "Description 3", R.drawable.woman_dancing, false));

        // Setup Top Stories RecyclerView
        topStoriesRecyclerView = view.findViewById(R.id.topStoriesRecyclerView);
        topStoriesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        topStoriesRecyclerView.setAdapter(new NewsAdapter(topStories, true, item -> {
            // Handle item click - open detail fragment
            ((MainActivity)requireActivity()).showNewsDetail(item);
        }));

        // Setup News RecyclerView
        newsRecyclerView = view.findViewById(R.id.newsRecyclerView);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        newsRecyclerView.setAdapter(new NewsAdapter(newsItems, true, item -> {
            // Handle item click - open detail fragment
            ((MainActivity)requireActivity()).showNewsDetail(item);
        }));

        return view;
    }
}