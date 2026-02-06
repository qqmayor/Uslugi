package com.uslugi.app;

import android.app.AlertDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder> {
    public interface OnReviewAddedListener {
        void onReviewAdded(ServiceItem item, String reviewText);
    }

    private final List<ServiceItem> items;
    private final OnReviewAddedListener reviewAddedListener;

    public ServiceAdapter(List<ServiceItem> items, OnReviewAddedListener reviewAddedListener) {
        this.items = items;
        this.reviewAddedListener = reviewAddedListener;
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_service, parent, false);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
        ServiceItem item = items.get(position);
        holder.title.setText(item.getTitle());
        holder.subtitle.setText(item.getSubtitle());
        holder.rating.setRating(item.getRating());
        holder.ratingValue.setText(String.format("%.1f", item.getRating()));
        holder.reviewCount.setText(holder.itemView.getResources().getString(
                R.string.review_count, item.getReviewCount()));
        holder.reviewsPreview.setText(TextUtils.join("\n", item.getReviews()));
        holder.reviewButton.setOnClickListener(v -> showReviewDialog(holder.itemView.getContext(), item));
    }

    private void showReviewDialog(Context context, ServiceItem item) {
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_review, null);
        EditText reviewInput = dialogView.findViewById(R.id.reviewInput);

        new AlertDialog.Builder(context)
                .setTitle(R.string.add_review_title)
                .setView(dialogView)
                .setPositiveButton(R.string.add_review_button, (dialog, which) -> {
                    String reviewText = reviewInput.getText().toString().trim();
                    if (!reviewText.isEmpty()) {
                        reviewAddedListener.onReviewAdded(item, reviewText);
                        notifyDataSetChanged();
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ServiceViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView subtitle;
        private final RatingBar rating;
        private final TextView ratingValue;
        private final TextView reviewCount;
        private final TextView reviewsPreview;
        private final Button reviewButton;

        ServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.serviceTitle);
            subtitle = itemView.findViewById(R.id.serviceSubtitle);
            rating = itemView.findViewById(R.id.serviceRating);
            ratingValue = itemView.findViewById(R.id.ratingValue);
            reviewCount = itemView.findViewById(R.id.reviewCount);
            reviewsPreview = itemView.findViewById(R.id.reviewPreview);
            reviewButton = itemView.findViewById(R.id.reviewButton);
        }
    }
}
