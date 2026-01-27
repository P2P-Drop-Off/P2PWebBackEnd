package com.p2p.server.p2p_backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.p2p.server.p2p_backend.repository.UserRepository;
import com.p2p.server.p2p_backend.service.UserService;
import java.util.hashMap;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository repository;
    private final UserService service;

    public UserController(UserRepository repository, UserService service) {
        this.repository = repository;
        this.service = service;
    }

    @GetMapping("/{id}")
    public String printUser(@PathVariable String id) throws Exception {
        repository.printUserById(id);
        return "Printed user to logs";
    }

    @PostMapping("/post/{id}")
    public String newUser(@PathVariable HashMap<String, String> data) throws Exception {
        System.out.println("data", data);
        service.setUser();
        return "bl";
    }
}
