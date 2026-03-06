package com.p2p.server.p2p_backend.dto.response;

public class CreateItemResponse {
    private String id;
    private String title;
    private String image;

    public CreateItemResponse(String id, String title, String image) {
        this.id = id;
        this.title = title;
        this.image = image;
    }

    // Getters
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getImage() { return image; }
}
