package com.uslugi.app.domain.model;

public class Review {
    private final String id;
    private final String serviceId;
    private final String author;
    private final String text;
    private final float rating;
    private final String date;

    public Review(String id, String serviceId, String author, String text, float rating, String date) {
        this.id = id;
        this.serviceId = serviceId;
        this.author = author;
        this.text = text;
        this.rating = rating;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public String getServiceId() {
        return serviceId;
    }

    public String getAuthor() {
        return author;
    }

    public String getText() {
        return text;
    }

    public float getRating() {
        return rating;
    }

    public String getDate() {
        return date;
    }
}
