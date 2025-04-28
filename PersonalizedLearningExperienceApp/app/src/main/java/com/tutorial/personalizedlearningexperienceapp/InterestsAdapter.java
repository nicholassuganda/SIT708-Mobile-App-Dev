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

    public InterestsAdapter(List<Interest> interests) {
        this.interests = interests;
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
        holder.interestTextView.setText(interest.getName());

        if (interest.isSelected()) {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.purple_200));
        } else {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.white));
        }

        holder.itemView.setOnClickListener(v -> {
            interest.setSelected(!interest.isSelected());
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return interests.size();
    }

    static class InterestViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView interestTextView;

        public InterestViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            interestTextView = itemView.findViewById(R.id.interestTextView);
        }
    }
}