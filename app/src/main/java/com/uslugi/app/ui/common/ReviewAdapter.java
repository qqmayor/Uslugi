package com.uslugi.app.ui.common;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uslugi.app.R;
import com.uslugi.app.domain.model.Review;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {
    private final List<Review> items = new ArrayList<>();

    public void submitList(List<Review> reviews) {
        items.clear();
        if (reviews != null) {
            items.addAll(reviews);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_review, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review review = items.get(position);
        holder.author.setText(review.getAuthor());
        holder.date.setText(review.getDate());
        holder.text.setText(review.getText());
        holder.ratingBar.setRating(review.getRating());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ReviewViewHolder extends RecyclerView.ViewHolder {
        private final TextView author;
        private final TextView date;
        private final TextView text;
        private final RatingBar ratingBar;

        ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.reviewAuthor);
            date = itemView.findViewById(R.id.reviewDate);
            text = itemView.findViewById(R.id.reviewText);
            ratingBar = itemView.findViewById(R.id.reviewRating);
        }
    }
}
