package com.p2p.server.p2p_backend.service;

import com.p2p.server.p2p_backend.model.User;
import com.p2p.server.p2p_backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.Map;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User createUser(User user) throws Exception {
        // Set UTC timestamp as ISO 8601 string for Firestore
        String utcTimestamp = Instant.now().toString();
        user.setCreatedAt(utcTimestamp);
        
        return repository.createUser(user);
    }

    public User getUser(String userId) throws Exception {
        return null;
    }

    public Boolean verifyMarketplaceLink(String link, User user) {
        // TODO: verify the link and verify it's from the sending user's linked account.
        //  Right now, have the user input data but later have it scrape.
        return false;
    }
}