package com.p2p.server.p2p_backend.dto.request;
import com.p2p.server.p2p_backend.model.Location;
import com.p2p.server.p2p_backend.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class CreateItemRequest {
    @NotBlank
    private String id;

    @NotBlank
    private User seller;

    @NotBlank
    @Positive
    private BigDecimal price;

    @NotBlank
    private String name;

    @NotBlank
    private String createdAt;

    @NotBlank
    private Location store;

    @NotBlank
    private String link;

    @NotBlank
    private String status;

    private String description;

    private String transactionId;
}
