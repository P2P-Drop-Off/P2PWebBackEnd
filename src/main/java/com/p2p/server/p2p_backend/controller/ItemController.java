package com.p2p.server.p2p_backend.controller;

import com.p2p.server.p2p_backend.dto.request.CreateItemRequest;
import com.p2p.server.p2p_backend.dto.response.CreateItemResponse;
import com.p2p.server.p2p_backend.dto.response.GetItemResponse;
import com.p2p.server.p2p_backend.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile; //1
import com.p2p.server.p2p_backend.service.ImageService; //1
import org.springframework.web.bind.annotation.ModelAttribute; //1
import java.util.List;

@RestController
@RequestMapping("/api")
public class ItemController {

    private final ItemService itemService;
    private final ImageService imageService;

    @Autowired
    public ItemController(ItemService itemService, ImageService imageService) {
        this.itemService = itemService;
        this.imageService = imageService;
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

    // CREATE a new item //1
    @PostMapping(value = "/items", consumes = "multipart/form-data")
    public ResponseEntity<CreateItemResponse> createItem(
            @ModelAttribute CreateItemRequest request,
            @RequestParam("imageFile") MultipartFile imageFile
    ) throws Exception {

        String imageUrl = imageService.uploadImage(imageFile);
        request.setImage(imageUrl);

        CreateItemResponse response = itemService.createItem(request);

        return ResponseEntity.ok(response);
    }
        

    // UPDATE an existing item
    @PutMapping("/items/{id}")
    public ResponseEntity<CreateItemResponse> updateItem(@PathVariable String id,
                                                         @RequestBody CreateItemRequest request) {
        CreateItemResponse response = itemService.updateItem(id, request);
        return ResponseEntity.ok(response);
    }

    // DELETE an item
    @DeleteMapping("/items/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable String id) {
        itemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }
}