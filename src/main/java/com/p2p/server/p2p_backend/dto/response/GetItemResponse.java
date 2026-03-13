package com.p2p.server.p2p_backend.dto.response;

import com.p2p.server.p2p_backend.model.Item;
import java.math.BigDecimal;
import com.google.cloud.Timestamp;

public class GetItemResponse {

    private String id;
    private String ownerUid;
    private String title;
    private String description;
    private BigDecimal price;
    private String image;
    private String location;
    private String locationId;
    private String status;
    private Timestamp createdAt;
    private String sixDigitCode;

    public GetItemResponse(Item item) {
        this.id = item.getId();
        this.ownerUid = item.getOwnerUid();
        this.title = item.getTitle();
        this.description = item.getDescription();
        this.price = item.getPrice();
        this.image = item.getImage();
        this.location = item.getLocation();
        this.locationId = item.getLocationId();
        this.status = item.getStatus();
        this.createdAt = item.getCreatedAt();
        this.sixDigitCode = item.getSixDigitCode();
    }

    // Getters
    public String getId() { return id; }
    public String getOwnerUid() { return ownerUid; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public BigDecimal getPrice() { return price; }
    public String getImage() { return image; }
    public String getLocation() { return location; }
    public String getLocationId() { return locationId; }
    public String getStatus() { return status; }
    public Timestamp getCreatedAt() { return createdAt; }
    public String getSixDigitCode() { return sixDigitCode; }
}