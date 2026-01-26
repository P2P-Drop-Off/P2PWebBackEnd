package com.p2p.server.p2p_backend.model;

import java.util.List;


public class User {
    private String id;

    private String firstName;
    private String lastName;
    private String email;
    private String city;
    private String state;
    private String zip;
    private String createdAt;
    private List<String> interests;

    public User() {}

    // id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // firstName
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // lastName
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // city
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    // state
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    // zip
    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    // createdAt
    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    // interests
    public List<String> getInterests() {
        return interests;
    }

    public void setInterests(List<String> interests) {
        this.interests = interests;
    }
}
