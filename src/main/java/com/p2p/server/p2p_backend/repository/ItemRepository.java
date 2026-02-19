package com.p2p.server.p2p_backend.repository;

import com.p2p.server.p2p_backend.exceptions.ItemNotFoundException;
import com.p2p.server.p2p_backend.model.Item;
import com.google.cloud.firestore.*;
import com.google.cloud.firestore.DocumentSnapshot;
import org.springframework.stereotype.Repository;

@Repository
public class ItemRepository {
    private final Firestore firestore;
    public ItemRepository(Firestore firestore) {
        this.firestore = firestore;
    }

    // READ
    public Item getItem(String itemId) throws Exception{
        try {
            DocumentSnapshot doc = firestore
                    .collection("items")
                    .document(itemId)
                    .get()
                    .get();

            if (!doc.exists()) {
                System.out.println("Item not found: " + itemId);
                throw new ItemNotFoundException("Item with id " + itemId + "not found");
            }

            Item item = doc.toObject(Item.class);
            if (item == null) {
                throw new IllegalAccessException("Failed to map Item: " + itemId);
            }

            return item;
        } catch (Exception e) {
            throw new Exception("Something went wrong while fetching item " + itemId, e);

        }
    }

    public void createItem(Item item) throws Exception{

    }

    // DELETE
    public void deleteItem(String itemId) throws Exception{
        try {
            firestore.collection("items").document(itemId).delete();
        } catch (Exception e) {
            throw new Exception("Failed to delete item with id: " + itemId, e);
        }
    }
}
