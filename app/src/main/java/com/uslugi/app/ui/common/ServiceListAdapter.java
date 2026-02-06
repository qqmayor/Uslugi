package com.uslugi.app.ui.common;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uslugi.app.R;
import com.uslugi.app.domain.model.Service;

import java.util.ArrayList;
import java.util.List;

public class ServiceListAdapter extends RecyclerView.Adapter<ServiceListAdapter.ServiceViewHolder> {
    public interface OnServiceClickListener {
        void onServiceClick(Service service);
    }

    private final List<Service> items = new ArrayList<>();
    private final OnServiceClickListener listener;

    public ServiceListAdapter(OnServiceClickListener listener) {
        this.listener = listener;
    }

    public void submitList(List<Service> services) {
        items.clear();
        if (services != null) {
            items.addAll(services);
        }
        notifyDataSetChanged();
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
        Service service = items.get(position);
        holder.name.setText(service.getName());
        holder.address.setText(service.getAddress());
        holder.category.setText(service.getCategory());
        holder.ratingValue.setText(String.format("%.1f", service.getRating()));
        holder.ratingBar.setRating(service.getRating());
        holder.itemView.setOnClickListener(v -> listener.onServiceClick(service));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ServiceViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView address;
        private final TextView category;
        private final TextView ratingValue;
        private final RatingBar ratingBar;

        ServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.serviceTitle);
            address = itemView.findViewById(R.id.serviceAddress);
            category = itemView.findViewById(R.id.serviceCategory);
            ratingValue = itemView.findViewById(R.id.serviceRatingValue);
            ratingBar = itemView.findViewById(R.id.serviceRating);
        }
    }
}
