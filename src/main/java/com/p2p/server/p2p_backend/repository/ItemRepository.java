package com.p2p.server.p2p_backend.repository;

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

        DocumentSnapshot doc = firestore
                .collection("items")
                .document(itemId)
                .get()
                .get();

        if (!doc.exists()) {
            System.out.println("Item not found: " + itemId);
            return null;
        }

        Item item = doc.toObject(Item.class);

        System.out.println("---- Got Item ----");
        System.out.println("Name: " + item.getName() + " " + item.getDescription());

        return doc.toObject(Item.class);

    }
}
