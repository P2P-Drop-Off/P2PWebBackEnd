package com.p2p.server.p2p_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.p2p.server.p2p_backend.service.UserService;
import com.p2p.server.p2p_backend.model.User;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @userSecurity.isOwner(authentication, id)")
    public User getUser(@PathVariable String id) throws Exception {
        User user = userService.getUser(id);
        if (user == null) {
            return null;
        }
        return user;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) throws Exception {
        User createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @PreAuthorize("hasRole('ADMIN') or @userSecurity.isOwner(authentication, id)")
    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user) throws Exception {
        User newUser = userService.updateUser(user);
        return ResponseEntity.ok(newUser);
    }

    @PreAuthorize("hasRole('ADMIN') or @userSecurity.isOwner(authentication, id)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) throws Exception {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}

