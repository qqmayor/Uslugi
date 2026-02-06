package com.uslugi.app.domain.model;

public class Booking {
    private final String id;
    private final String serviceId;
    private final String serviceName;
    private final String date;
    private final String time;
    private final String status;

    public Booking(String id, String serviceId, String serviceName, String date, String time, String status) {
        this.id = id;
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.date = date;
        this.time = time;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getServiceId() {
        return serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getStatus() {
        return status;
    }
}
