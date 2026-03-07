package com.p2p.server.p2p_backend.service;

import com.p2p.server.p2p_backend.model.Item;
import com.p2p.server.p2p_backend.repository.ItemRepository;
import com.p2p.server.p2p_backend.dto.request.CreateItemRequest;
import com.p2p.server.p2p_backend.dto.response.CreateItemResponse;
import com.p2p.server.p2p_backend.dto.response.GetItemResponse;
import com.p2p.server.p2p_backend.exceptions.ItemNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
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

    

    public GetItemResponse getItemById(String id) {
        try {
            Item item = itemRepository.getItem(id);
            if (item == null) throw new ItemNotFoundException("Item not found: " + id);
            return new GetItemResponse(item);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch item: " + id, e);
        }
    }

    // Create new item
    public CreateItemResponse createItem(CreateItemRequest request) {
        try {
            Item item = new Item();
            item.setTitle(request.getTitle());
            item.setDescription(request.getDescription());
            item.setPrice(request.getPrice());
            item.setImage(request.getImage());
            item.setLocation(request.getLocation());
            item.setViews(0);
            item.setComments(0);
            item.setStatus("active");

            Item saved = itemRepository.createItem(item);
            return new CreateItemResponse(saved.getId(), saved.getTitle());
        } catch (Exception e) {
            throw new RuntimeException("Failed to create item", e);
        }
    }

    // Update existing item
    public CreateItemResponse updateItem(String id, CreateItemRequest request) {
        try {
            Item item = itemRepository.getItem(id);
            if (item == null) throw new ItemNotFoundException("Item not found: " + id);

            item.setTitle(request.getTitle());
            item.setDescription(request.getDescription());
            item.setPrice(request.getPrice());
            item.setImage(request.getImage());
            item.setLocation(request.getLocation());

            Item updated = itemRepository.updateItem(item);
            return new CreateItemResponse(updated.getId(), updated.getTitle());
        } catch (Exception e) {
            throw new RuntimeException("Failed to update item: " + id, e);
        }
    }

    // Delete item
    public void deleteItem(String id) {
        try {
            itemRepository.deleteItem(id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete item: " + id, e);
        }
    }
}