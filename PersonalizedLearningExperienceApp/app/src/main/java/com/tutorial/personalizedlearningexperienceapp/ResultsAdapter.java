package com.tutorial.personalizedlearningexperienceapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ResultViewHolder> {

    private List<ResultItem> resultItems;

    public ResultsAdapter(List<ResultItem> resultItems) {
        this.resultItems = resultItems;
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_result, parent, false);
        return new ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder holder, int position) {
        ResultItem item = resultItems.get(position);
        holder.labelTextView.setText(item.getLabel());
        holder.valueTextView.setText(item.getValue());
    }

    @Override
    public int getItemCount() {
        return resultItems.size();
    }

    static class ResultViewHolder extends RecyclerView.ViewHolder {
        TextView labelTextView, valueTextView;

        public ResultViewHolder(@NonNull View itemView) {
            super(itemView);
            labelTextView = itemView.findViewById(R.id.labelTextView);
            valueTextView = itemView.findViewById(R.id.valueTextView);
        }
    }
}
