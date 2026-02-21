package com.p2p.server.p2p_backend.controller;

import com.p2p.server.p2p_backend.model.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.p2p.server.p2p_backend.service.ItemService;
import com.p2p.server.p2p_backend.model.Item;
import com.p2p.server.p2p_backend.dto.response.CreateItemResponse;
import com.p2p.server.p2p_backend.dto.request.CreateItemRequest;

@RestController
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItem(@PathVariable String id) throws Exception {

        Item item = itemService.getItem(id);

        if (item == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(item);
    }

    @PostMapping
    public ResponseEntity<Item> createItem(@RequestBody Item item) throws Exception {
        Item createdItem = itemService.createItem(item);
        return ResponseEntity.ok(createdItem);
    }

    @PutMapping
    public ResponseEntity<Item> updateItem(@RequestBody Item item) throws Exception {
        Item updatedItem = itemService.updateItem(item);
        return ResponseEntity.ok(updatedItem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) throws Exception {
        itemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }

}
