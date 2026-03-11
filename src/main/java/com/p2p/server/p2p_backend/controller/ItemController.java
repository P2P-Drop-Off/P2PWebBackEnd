package com.p2p.server.p2p_backend.controller;

import com.p2p.server.p2p_backend.dto.request.CreateItemRequest;
import com.p2p.server.p2p_backend.dto.response.CreateItemResponse;
import com.p2p.server.p2p_backend.dto.response.GetItemResponse;
import com.p2p.server.p2p_backend.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile; 
import com.p2p.server.p2p_backend.service.ImageService; 
import org.springframework.web.bind.annotation.ModelAttribute; 
import java.util.List;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ItemController {

    private final ItemService itemService;
    private final ImageService imageService;

    private FirebaseToken verifyToken(HttpServletRequest request) throws Exception {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new Exception("No Authorization header found"); // use Exception
        }

        String idToken = authHeader.replace("Bearer ", "");
        return FirebaseAuth.getInstance().verifyIdToken(idToken);
    }

    @Autowired
    public ItemController(ItemService itemService, ImageService imageService) {
        this.itemService = itemService;
        this.imageService = imageService;
    }

    @GetMapping("/items")
    public ResponseEntity<List<GetItemResponse>> getAllItems(HttpServletRequest request) {
        try {
            FirebaseToken decodedToken = verifyToken(request);
            if (decodedToken == null) {
                return ResponseEntity.status(403).build();
            }
            return ResponseEntity.ok(itemService.getAllItems());
        } catch (FirebaseAuthException e) {
            return ResponseEntity.status(403).build();
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    // GET item by ID
    @GetMapping("/items/{id}")
    public ResponseEntity<GetItemResponse> getItemById(@PathVariable String id) {
        GetItemResponse response = itemService.getItemById(id);
        return ResponseEntity.ok(response);
    }

    // GET items for specific store
    @GetMapping("/stores/{locationId}/items")
    public ResponseEntity<List<GetItemResponse>> getItemsForStore(@PathVariable String locationId) {
        List<GetItemResponse> items = itemService.getItemsForStore(locationId);
        return ResponseEntity.ok(items);
    }

    // CREATE a new item
    @PostMapping(value = "/items", consumes = "multipart/form-data")
    public ResponseEntity<CreateItemResponse> createItem(
            HttpServletRequest request,
            @ModelAttribute CreateItemRequest createRequest,
            @RequestParam("imageFile") MultipartFile imageFile
    ) throws Exception {
        try {
            FirebaseToken decodedToken = verifyToken(request);
            String uid = decodedToken.getUid();

            // Upload image
            String imageUrl = imageService.uploadImage(imageFile);
            createRequest.setImage(imageUrl);

            // Set owner UID
            createRequest.setOwnerUid(uid);

            CreateItemResponse response = itemService.createItem(createRequest);

            return ResponseEntity.ok(response);
        } catch (FirebaseAuthException e) {
            return ResponseEntity.status(403).build(); // Forbidden if token invalid
        }
    }
        

    // UPDATE an existing item
    @PutMapping("/items/{id}")
    public ResponseEntity<CreateItemResponse> updateItem(@PathVariable String id,
                                                         @RequestBody CreateItemRequest request) {
        CreateItemResponse response = itemService.updateItem(id, request);
        return ResponseEntity.ok(response);
    }

    //controller endpoint for seller updating payment status
    @PutMapping("/items/{id}/status")
    public ResponseEntity<String> updateItemPaymentStatus(@PathVariable String id,
                                                         @RequestBody Map<String, String> body) {
        try {
            String newStatus = body.get("status");
            itemService.updateItemStatus(id, newStatus);
            return ResponseEntity.ok(newStatus);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    //controller exndpoint for buyer approving transaction
    @PutMapping("/items/{id}/approve")
    public ResponseEntity<CreateItemResponse> approveTransaction(@PathVariable String id) throws Exception {
        itemService.approveTransaction(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/partner/items/{itemId}/update-status")
    public ResponseEntity<String> updateItemStatus(@PathVariable String itemId, @RequestBody Map<String, String> body) {
        try {
            String newStatus = body.get("status"); // "dropped_off" or "payment_received" or "picked_up"
            itemService.updateItemStatus(itemId, newStatus);
            return ResponseEntity.ok(newStatus);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PutMapping("/partner/items/{id}/dropoff")
    public ResponseEntity<?> dropOffItem(@PathVariable String id) {
        try {
            itemService.updateItemStatus(id, "dropped_off");
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }


    // DELETE an item
    @DeleteMapping("/items/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable String id) throws Exception {
        itemService.deleteItem(id);
        return ResponseEntity.ok("item deleted"); //?
    }
}