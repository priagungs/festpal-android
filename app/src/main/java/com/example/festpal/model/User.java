package com.example.festpal.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User {

    @SerializedName("_id")
    @Expose
    String id;

    @SerializedName("name")
    @Expose
    String name;

    @SerializedName("email")
    @Expose
    String email;

    @SerializedName("address")
    @Expose
    String address;

    @SerializedName("phone")
    @Expose
    String phone;

    @SerializedName("isUMKM")
    @Expose
    Boolean isUMKM;

    @SerializedName("business")
    @Expose
    Business business;

    @SerializedName("bookedStands")
    @Expose
    List<Booking> bookedStands;

    @SerializedName("favoriteFestivals")
    @Expose
    List<String> favoriteFestivals;

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    String picture;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getUMKM() {
        return isUMKM;
    }

    public void setUMKM(Boolean UMKM) {
        isUMKM = UMKM;
    }

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    public List<Booking> getBookedStands() {
        return bookedStands;
    }

    public void setBookedStands(List<Booking> bookedStands) {
        this.bookedStands = bookedStands;
    }

    public List<String> getFavoriteFestivals() {
        return favoriteFestivals;
    }

    public void setFavoriteFestivals(List<String> favoriteFestivals) {
        this.favoriteFestivals = favoriteFestivals;
    }
}
