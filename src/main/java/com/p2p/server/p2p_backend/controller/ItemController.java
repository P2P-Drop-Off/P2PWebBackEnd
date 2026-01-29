package com.p2p.server.p2p_backend.controller;

import com.p2p.server.p2p_backend.model.User;
import com.p2p.server.p2p_backend.repository.UserRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.p2p.server.p2p_backend.repository.ItemRepository;
import com.p2p.server.p2p_backend.model.Item;

@RestController
@RequestMapping("/items")
public class ItemController {
    private final ItemRepository repository;

    public ItemController(ItemRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItem(@PathVariable String id) throws Exception {
        Item item = repository.getItem(id);

        if (item == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(item);
    }
}
