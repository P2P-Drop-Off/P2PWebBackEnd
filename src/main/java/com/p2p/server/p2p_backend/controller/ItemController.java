package com.p2p.server.p2p_backend.controller;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile; //1
import com.p2p.server.p2p_backend.service.ImageService; //1
import org.springframework.web.bind.annotation.ModelAttribute; //1
import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/items")
    public ResponseEntity<List<GetItemResponse>> getAllItems() {
        return ResponseEntity.ok(itemService.getAllItems());
    }

    // GET item by ID
    @GetMapping("/items/{id}")
    public ResponseEntity<GetItemResponse> getItemById(@PathVariable String id) {
        GetItemResponse response = itemService.getItemById(id);
        return ResponseEntity.ok(response);
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
