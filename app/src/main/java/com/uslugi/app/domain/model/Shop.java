package com.uslugi.app.domain.model;

public class Shop {
    private final String id;
    private final String name;
    private final String address;
    private final String category;
    private final float rating;

    public Shop(String id, String name, String address, String category, float rating) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.category = category;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCategory() {
        return category;
    }

    public float getRating() {
        return rating;
    }
}
