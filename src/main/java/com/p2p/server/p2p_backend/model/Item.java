package com.p2p.server.p2p_backend.model;
import com.google.cloud.firestore.DocumentReference;

import java.math.BigDecimal;

public class Item {
    public static final String PATH = "items";

    private String id;

    private String seller;
    private String name;
    private String createdAt;
    private String description;
    private String store;
    private String link;
    private String status;
    private String transactionId;
    private BigDecimal price;
    private String title;
    private String image;
    private String location;
    private String locationId;
    private String imageUrl;
    private int views;
    private int comments;

    public Item(){}

    // id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getLocationId() { return locationId; }

    public void setLocationId(String locationId) { this.locationId = locationId; }

    public String getImageUrl() { return imageUrl; }

    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public int getViews() { return views; }
    public void setViews(int views) { this.views = views; }

    public int getComments() { return comments; }
    public void setComments(int comments) { this.comments = comments; }

    // name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // seller (User)
    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    // store (Location)
    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    // link
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    // createdAt
    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    // status
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // transactionId
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    // Price
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}


