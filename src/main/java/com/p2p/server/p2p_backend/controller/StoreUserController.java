package com.p2p.server.p2p_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import com.p2p.server.p2p_backend.repository.StoreUserRepository;
import com.p2p.server.p2p_backend.service.StoreUserService;
import com.p2p.server.p2p_backend.model.StoreUser;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/storeUsers")
public class StoreUserController {

    private final StoreUserRepository repository;
    private final StoreUserService service;

    public StoreUserController(StoreUserRepository repository, StoreUserService service) {
        this.repository = repository;
        this.service = service;
    }

    @GetMapping("/test")
    public ResponseEntity<Map<String, String>> test() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "ok");
        response.put("message", "StoreUser endpoint is working");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoreUser> getStoreUser(@PathVariable String id) throws Exception {
        StoreUser storeUser = repository.getStoreUser(id);

        if (storeUser == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(storeUser);
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> newStoreUser(@RequestBody StoreUser storeUser) {
        System.out.println("===== POST /storeUsers/post called =====");
        System.out.println("Received storeUser: " + storeUser.getFirstName() + " " + storeUser.getLastName());
        
        try {
            StoreUser createdStoreUser = service.createStoreUser(storeUser);
            
            System.out.println("StoreUser created successfully with ID: " + createdStoreUser.getId());
            
            Map<String, String> response = new HashMap<>();
            response.put("id", createdStoreUser.getId());
            response.put("status", "success");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("ERROR creating storeUser: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", e.getMessage());
            
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
}
