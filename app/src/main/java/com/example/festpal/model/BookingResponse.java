package com.example.festpal.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookingResponse {
    @SerializedName("_id")
    @Expose
    String id;

    @SerializedName("event")
    @Expose
    Event event;

    @SerializedName("status")
    @Expose
    String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
