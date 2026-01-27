package com.p2p.server.p2p_backend.model;

import java.util.List;
import java.time.*;

@Entity
public class User {

    @Id
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
