package com.p2p.server.p2p_backend.service;

import com.p2p.server.p2p_backend.exceptions.ItemNotFoundException;
import com.p2p.server.p2p_backend.model.Item;
import com.p2p.server.p2p_backend.model.StoreUser;
import com.p2p.server.p2p_backend.model.User;
import com.p2p.server.p2p_backend.repository.ItemRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class ItemService {

    private final ItemRepository repository;

    public ItemService(ItemRepository repository){
        this.repository = repository;
    }

    public Item createItem(Item item) throws Exception{
        String utcTimestamp = Instant.now().toString();
        item.setCreatedAt(utcTimestamp);
        return repository.createItem(item);
    }

    @PreAuthorize("hasRole('ADMIN') or @userSecurity.isOwner(authentication, id)")
    public Item getItem(String itemId) throws Exception {
        return repository.getItem(itemId);
    }

    public Item updateItem(Item item) throws Exception {
        return repository.updateItem(item);
    }

    public void deleteItem(String itemId) throws Exception {
        repository.deleteItem(itemId);
    }
}
