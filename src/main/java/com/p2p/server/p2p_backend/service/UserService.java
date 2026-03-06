package com.p2p.server.p2p_backend.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.ErrorCode;
import com.google.firebase.auth.FirebaseAuthException;
import com.p2p.server.p2p_backend.exceptions.ItemNotFoundException;
import com.p2p.server.p2p_backend.model.StoreUser;
import com.p2p.server.p2p_backend.model.User;
import com.p2p.server.p2p_backend.repository.StoreUserRepository;
import com.p2p.server.p2p_backend.repository.UserRepository;
import org.apache.catalina.Store;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository repository;
    private final StoreUserRepository storeUserRepository;

    public UserService(UserRepository repository, StoreUserRepository storeUserRepository) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        this.repository = repository;
        this.storeUserRepository = storeUserRepository;
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