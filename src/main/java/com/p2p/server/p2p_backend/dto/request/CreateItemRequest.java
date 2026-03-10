package com.p2p.server.p2p_backend.dto.request;
import com.p2p.server.p2p_backend.model.Location;
import com.p2p.server.p2p_backend.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class CreateItemRequest {

    @NotBlank
    private String title;

    private String ownerUid;

    private String description;

    @NotNull
    @Positive
    private BigDecimal price;

    @NotBlank
    private String image;

    @NotBlank
    private String location;

    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getOwnerUid() { return ownerUid; }
    public void setOwnerUid(String ownerUid) { this.ownerUid = ownerUid; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
}