package com.uslugi.app.domain.model;

public class Service {
    private final String id;
    private final String name;
    private final String description;
    private final String address;
    private final String category;
    private final double latitude;
    private final double longitude;
    private float rating;

    public Service(String id, String name, String description, String address, String category,
                   double latitude, double longitude, float rating) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.category = category;
        this.latitude = latitude;
        this.longitude = longitude;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getAddress() {
        return address;
    }

    public String getCategory() {
        return category;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
