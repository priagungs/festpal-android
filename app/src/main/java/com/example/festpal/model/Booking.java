package com.example.festpal.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Booking {
    @SerializedName("_id")
    @Expose
    String id;

    @SerializedName("event")
    @Expose
    String idEvent;

    @SerializedName("status")
    @Expose
    Boolean status;
}
