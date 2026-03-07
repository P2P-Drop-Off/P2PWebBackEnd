package com.p2p.server.p2p_backend.service;

import com.p2p.server.p2p_backend.model.Item;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

// This handles ALL security for controllers

@Service("userSecurity")
public class UserSecurity {

    @Autowired
    private UserService userService;
    @Autowired
    private ItemService itemService;

    public boolean isOwner(Authentication authentication, String userId) {
        if (authentication == null) return false;
        return authentication.getName().equals(userId);
    }

    public boolean ownsItem(Authentication authentication, String itemId) throws Exception {
        // Checks if User owns an Item
        if (authentication == null) return false;
        Item item = itemService.getItem(itemId);
        String uid = authentication.getName();
        return item.getSeller().equals(uid);
    }
}
