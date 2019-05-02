package com.example.asm_server.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {


    @SerializedName("_id")
    @Expose
    private String id;


    @SerializedName("fullName")
    @Expose
    private String name;


    @SerializedName("email")
    @Expose
    private String email;


    @SerializedName("mobile")
    @Expose
    private String phoneNumber;


    @SerializedName("city")
    @Expose
    private String city;

    public User() {
    }

    public User(String id, String name, String email, String phoneNumber, String city) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.city = city;
    }

    public User(String name, String email, String phoneNumber, String city) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.city = city;
    }

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
