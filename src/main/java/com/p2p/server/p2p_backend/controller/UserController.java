package com.p2p.server.p2p_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import com.p2p.server.p2p_backend.repository.UserRepository;
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
    public ResponseEntity<User> printUser(@PathVariable String id) throws Exception {
        User user = userService.getUser(id);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user);
    }

    @PostMapping("/post")
    public ResponseEntity<User> newUser(@RequestBody User user) throws Exception {
        User createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }
}

