package com.uslugi.app.ui.common;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uslugi.app.R;
import com.uslugi.app.domain.model.Booking;

import java.util.ArrayList;
import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {
    private final List<Booking> items = new ArrayList<>();

    public void submitList(List<Booking> bookings) {
        items.clear();
        if (bookings != null) {
            items.addAll(bookings);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_booking, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        Booking booking = items.get(position);
        holder.title.setText(booking.getServiceName());
        holder.date.setText(booking.getDate());
        holder.time.setText(booking.getTime());
        holder.status.setText(booking.getStatus());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class BookingViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView date;
        private final TextView time;
        private final TextView status;

        BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.bookingTitle);
            date = itemView.findViewById(R.id.bookingDate);
            time = itemView.findViewById(R.id.bookingTime);
            status = itemView.findViewById(R.id.bookingStatus);
        }
    }
}
