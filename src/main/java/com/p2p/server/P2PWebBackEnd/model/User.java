package com.p2p.server.P2PWebBackEnd.model;

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

    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return this.id;
    }
    public String getFirstName() {
        return this.firstName;
    }
    public String getLastName() {
        return this.lastName;
    }
}
