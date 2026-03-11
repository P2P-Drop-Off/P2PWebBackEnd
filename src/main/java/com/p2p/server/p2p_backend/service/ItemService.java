package com.p2p.server.p2p_backend.service;

import com.p2p.server.p2p_backend.dto.request.CreateItemRequest;
import com.p2p.server.p2p_backend.dto.response.CreateItemResponse;
import com.p2p.server.p2p_backend.dto.response.GetItemResponse;
import com.p2p.server.p2p_backend.exceptions.ItemNotFoundException;
import com.p2p.server.p2p_backend.model.Item;
import com.p2p.server.p2p_backend.repository.FirestoreItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.stream.Collectors;

import java.util.List;

@Service
public class ItemService {

    private final FirestoreItemRepository itemRepository;

    public ItemService(FirestoreItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    private String generateSixDigitCode() throws Exception { // generates a unique 6-digit code for item drop-off and pick-up
        String code;

        do {
            int num = (int)(Math.random() * 900000) + 100000;
            code = String.valueOf(num);

        } while (itemRepository.sixDigitCodeExists(code)); // check Firebase

        return code;
    }

    public List<GetItemResponse> getAllItems() {
    try {
        List<Item> items = itemRepository.getAllItems();

        return items.stream()
                .map(GetItemResponse::new)
                .toList();

    } catch (Exception e) {
        throw new RuntimeException("Failed to fetch items", e);
    }
    }

    public List<GetItemResponse> getAllItemsForUser(String uid) {
        // fetch all items
        List<GetItemResponse> allItems = getAllItems();

        // filter to only include items owned by this UID
        return allItems.stream()
                .filter(item -> uid.equals(item.getOwnerUid()))
                .collect(Collectors.toList());
    }

    public Item getItem(String id) {
        try {
            Item item = itemRepository.getItem(id);
            return itemRepository.getItem(id);
        } catch (ItemNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch item: " + id, e);
        }
    }

    public GetItemResponse getItemById(String id) {
        try {
            Item item = itemRepository.getItem(id);
            return new GetItemResponse(item);
        } catch (ItemNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch item: " + id, e);
        }
    }

    public CreateItemResponse createItem(CreateItemRequest request) {
        try {
            Item item = new Item();

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String uid = (String) auth.getPrincipal(); 
            
            item.setTitle(request.getTitle());
            item.setDescription(request.getDescription());
            item.setPrice(request.getPrice());
            item.setImage(request.getImage());
            item.setLocation(request.getLocation());
            item.setViews(0);
            item.setComments(0);
            item.setStatus("active");
            item.setOwnerUid(uid); 

            Item saved = itemRepository.createItem(item);
            return new CreateItemResponse(saved.getId(), saved.getTitle(), saved.getImage());
        } catch (Exception e) {
            throw new RuntimeException("Failed to create item", e);
        }
    }

    public CreateItemResponse updateItem(String id, CreateItemRequest request) {
        try {
            Item item = itemRepository.getItem(id);

            item.setTitle(request.getTitle());
            item.setDescription(request.getDescription());
            item.setPrice(request.getPrice());
            item.setImage(request.getImage());
            item.setLocation(request.getLocation());

            Item updated = itemRepository.updateItem(item);
            return new CreateItemResponse(updated.getId(), updated.getTitle(), updated.getImage());
        } catch (ItemNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to update item: " + id, e);
        }
    }

    public void deleteItem(String id) throws Exception {
        Item item = itemRepository.getItem(id);

        if (!item.getStatus().equals("active")) {
            throw new RuntimeException("Only active listings can be deleted");
        }
        
        itemRepository.deleteItem(id);
    }

    public void approveTransaction(String itemId) throws Exception {

        Item item = itemRepository.getItem(itemId);

        if (!item.getStatus().equals("active")) {
            throw new RuntimeException("Item cannot be approved");
        }

        String sixDigitCode = generateSixDigitCode();

        item.setStatus("approved_by_buyer");
        item.setSixDigitCode(sixDigitCode);

        itemRepository.updateItem(item);
    }

    public void updateItemStatus(String itemId, String status) {
        try {
            // Fetch item using Firestore repo method
            Item item = itemRepository.getItem(itemId);
            if (item == null) {
                throw new RuntimeException("Item not found");
            }

            // Update status
            item.setStatus(status);

            // Persist the change using Firestore repo
            itemRepository.updateItem(item);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update item status for item: " + itemId, e);
        }
    }
}