package com.p2p.server.p2p_backend.service;

import com.p2p.server.p2p_backend.model.User;
import com.p2p.server.p2p_backend.repository.UserRepository;
import org.springframework.http.ResponseEntity;
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
        String utcTimestamp = Instant.now().toString();
        user.setCreatedAt(utcTimestamp);
        return repository.createUser(user);
    }

    public User getUser(String userId) throws Exception {
        return repository.getUser(userId);
    }

    public User updateUser(User user) throws Exception {
        return repository.updateUser(user);
    }

    public void deleteUser(String userId) throws Exception {
        repository.deleteUser(userId);
    }

}