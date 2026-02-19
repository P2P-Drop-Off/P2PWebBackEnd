package com.p2p.server.p2p_backend.model;
import jakarta.persistence.*;
import java.math.BigDecimal;

public class Item {

    private String id;

    private User seller;
    private String name;
    private String createdAt;
    private String description;
    private Location store;
    private String link;
    private String status;
    private String transactionId;
    private BigDecimal price;

    public Item(){}

    // id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    // store (Location)
    public Location getStore() {
        return store;
    }

    public void setStore(Location store) {
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

    // transactionId
    public BigDecimal getPrice() {
        return price;
    }

    public void setTransactionId(BigDecimal price) {
        this.price = price;
    }
}
