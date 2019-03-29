package com.example.festpal.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Event {

    @SerializedName("_id")
    @Expose
    String id;

    @SerializedName("name")
    @Expose
    String name;

    @SerializedName("date")
    @Expose
    Date date;

    @SerializedName("venue")
    @Expose
    String venue;

    @SerializedName("desc")
    @Expose
    String description;

    @SerializedName("image")
    @Expose
    String image;

    @SerializedName("standCapacity")
    @Expose
    Integer standCapacity;

    @SerializedName("availableStands")
    @Expose
    Integer availableStands;

    @SerializedName("pricePerStands")
    @Expose
    Integer pricePerStands;

    @SerializedName("favoritedCount")
    @Expose
    Integer favoritedCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getStandCapacity() {
        return standCapacity;
    }

    public void setStandCapacity(Integer standCapacity) {
        this.standCapacity = standCapacity;
    }

    public Integer getAvailableStands() {
        return availableStands;
    }

    public void setAvailableStands(Integer availableStands) {
        this.availableStands = availableStands;
    }

    public Integer getPricePerStands() {
        return pricePerStands;
    }

    public void setPricePerStands(Integer pricePerStands) {
        this.pricePerStands = pricePerStands;
    }

    public Integer getFavoritedCount() {
        return favoritedCount;
    }

    public void setFavoritedCount(Integer favoritedCount) {
        this.favoritedCount = favoritedCount;
    }
}
