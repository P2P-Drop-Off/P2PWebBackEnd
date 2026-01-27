package com.p2p.server.p2p_backend.controller;

import com.p2p.server.p2p_backend.model.User;
import com.p2p.server.p2p_backend.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.p2p.server.p2p_backend.repository.StoreUserRepository;
import com.p2p.server.p2p_backend.model.StoreUser;

@RestController
@RequestMapping("/storeUsers")
public class StoreUserController {
    private final StoreUserRepository repository;

    public StoreUserController(StoreUserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoreUser> printUser(@PathVariable String id) throws Exception {
        StoreUser user = repository.getStoreUser(id);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user);
    }

}
