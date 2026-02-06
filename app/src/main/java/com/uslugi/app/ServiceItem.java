package com.uslugi.app;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class ServiceItem {
    private final String title;
    private final String subtitle;
    private final float rating;
    private final LatLng location;
    private final List<String> reviews;

    public ServiceItem(String title, String subtitle, float rating, LatLng location, List<String> reviews) {
        this.title = title;
        this.subtitle = subtitle;
        this.rating = rating;
        this.location = location;
        this.reviews = new ArrayList<>(reviews);
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public float getRating() {
        return rating;
    }

    public LatLng getLocation() {
        return location;
    }

    public List<String> getReviews() {
        return new ArrayList<>(reviews);
    }

    public int getReviewCount() {
        return reviews.size();
    }

    public void addReview(String review) {
        reviews.add(review);
    }
}
