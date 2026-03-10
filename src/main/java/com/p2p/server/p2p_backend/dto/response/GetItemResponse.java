package com.p2p.server.p2p_backend.dto.response;

import com.p2p.server.p2p_backend.model.Item;
import java.math.BigDecimal;

public class GetItemResponse {

    private String id;
    private String ownerUid;
    private String title;
    private String description;
    private BigDecimal price;
    private String image;
    private String location;
    private String status;

    public GetItemResponse(Item item) {
        this.id = item.getId();
        this.ownerUid = item.getOwnerUid();
        this.title = item.getTitle();
        this.description = item.getDescription();
        this.price = item.getPrice();
        this.image = item.getImage();
        this.location = item.getLocation();
        this.status = item.getStatus();
    }

    // Getters
    public String getId() { return id; }
    public String getOwnerUid() { return ownerUid; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public BigDecimal getPrice() { return price; }
    public String getImage() { return image; }
    public String getLocation() { return location; }
    public String getStatus() { return status; }
}