package com.example.festpal.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserRequest {

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

    @SerializedName("businessName")
    @Expose
    String businessName;

    @SerializedName("businessSector")
    @Expose
    String businessSector;

    @SerializedName("businessDesc")
    @Expose
    String businessDesc;

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

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessSector() {
        return businessSector;
    }

    public void setBusinessSector(String businessSector) {
        this.businessSector = businessSector;
    }

    public String getBusinessDesc() {
        return businessDesc;
    }

    public void setBusinessDesc(String businessDesc) {
        this.businessDesc = businessDesc;
    }
}
