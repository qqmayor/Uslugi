package com.uslugi.app.domain;

import androidx.lifecycle.LiveData;

import com.uslugi.app.domain.model.Booking;
import com.uslugi.app.domain.model.Review;
import com.uslugi.app.domain.model.Service;

import java.util.List;

public interface ServiceRepository {
    LiveData<List<Service>> getPopularServices();

    LiveData<List<String>> getCategories();

    LiveData<List<Service>> searchServices(String query, String category);

    LiveData<Service> getServiceById(String serviceId);

    LiveData<List<Review>> getReviews(String serviceId);

    void addReview(String serviceId, Review review);

    LiveData<List<Booking>> getUpcomingBookings();

    LiveData<List<Booking>> getPastBookings();

    void addBooking(Booking booking);
}
