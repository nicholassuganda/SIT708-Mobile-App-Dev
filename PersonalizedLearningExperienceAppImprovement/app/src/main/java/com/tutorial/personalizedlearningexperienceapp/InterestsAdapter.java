package com.tutorial.personalizedlearningexperienceapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class InterestsAdapter extends RecyclerView.Adapter<InterestsAdapter.InterestViewHolder> {

    private List<Interest> interests;
    private OnInterestClickListener listener;

    public interface OnInterestClickListener {
        void onInterestClick(int position, boolean isSelected);
    }

    public InterestsAdapter(List<Interest> interests, OnInterestClickListener listener) {
        this.interests = interests;
        this.listener = listener;
    }

    @NonNull
    @Override
    public InterestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_interest, parent, false);
        return new InterestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InterestViewHolder holder, int position) {
        Interest interest = interests.get(position);
        holder.bind(interest);

        holder.itemView.setOnClickListener(v -> {
            boolean newState = !interest.isSelected();
            interest.setSelected(newState);
            holder.bind(interest);
            listener.onInterestClick(position, newState);
        });
    }

    @Override
    public int getItemCount() {
        return interests.size();
    }

    static class InterestViewHolder extends RecyclerView.ViewHolder {
        private final CardView cardView;
        private final TextView interestTextView;

        public InterestViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            interestTextView = itemView.findViewById(R.id.interestTextView);
        }

        public void bind(Interest interest) {
            interestTextView.setText(interest.getName());
            if (interest.isSelected()) {
                cardView.setCardBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.purple_500));
                interestTextView.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.white));
            } else {
                cardView.setCardBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.white));
                interestTextView.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.black));
            }
        }
    }
}