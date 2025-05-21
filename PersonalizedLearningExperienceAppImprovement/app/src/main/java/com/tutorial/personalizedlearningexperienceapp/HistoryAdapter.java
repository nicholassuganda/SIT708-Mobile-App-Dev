package com.tutorial.personalizedlearningexperienceapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private List<QuizResult> results;

    public HistoryAdapter(List<QuizResult> results) {
        this.results = results;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QuizResult result = results.get(position);
        holder.topicText.setText(result.getTopic());
        holder.scoreText.setText(String.format("%d/%d", result.getScore(), result.getTotal()));
        holder.percentageText.setText(result.getPercentage());
        holder.dateText.setText(result.getDate());
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView topicText, scoreText, percentageText, dateText;

        public ViewHolder(View view) {
            super(view);
            topicText = view.findViewById(R.id.topicText);
            scoreText = view.findViewById(R.id.scoreText);
            percentageText = view.findViewById(R.id.percentageText);
            dateText = view.findViewById(R.id.dateText);
        }
    }
}
