package com.p2p.server.p2p_backend.model;

import java.util.List;

public class StoreUser {
    private String id;

    private String firstName;
    private String lastName;
    private String email;
    private String createdAt;
    private String phone;
    private String website;
    private Boolean available;

    public StoreUser() {}

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

    // createdAt
    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    // website
    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    // phone
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available){ this.available = available; }
}
