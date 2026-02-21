package com.p2p.server.p2p_backend.repository;

import com.google.api.core.ApiFuture;
import com.p2p.server.p2p_backend.exceptions.ItemNotFoundException;
import com.p2p.server.p2p_backend.model.Item;
import com.google.cloud.firestore.*;
import com.google.cloud.firestore.DocumentSnapshot;
import com.p2p.server.p2p_backend.model.User;
import org.springframework.stereotype.Repository;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

@Repository
public class ItemRepository {
    private final Firestore firestore;
    public ItemRepository(Firestore firestore) {
        this.firestore = firestore;
    }

    // READ
    public Item getItem(String itemId) throws RuntimeException, Exception {
        try {
            DocumentSnapshot doc = firestore
                    .collection(Item.PATH)
                    .document(itemId)
                    .get().get();

            if (!doc.exists()) {
                throw new ItemNotFoundException(itemId);
            }
            return doc.toObject(Item.class);
        } catch (InterruptedException e) {
            //Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted while fetching item", e);
        } catch (ExecutionException e) {
            throw new RuntimeException("Failed to fetch item from database", e);
        }
    }

    public Item createItem(Item item) throws Exception {
        try {
            DocumentReference docRef = firestore.collection(Item.PATH).document();
            item.setId(docRef.getId());
            docRef.set(item).get();
            return getItem(item.getId());
        } catch (CancellationException e) {
            throw new RuntimeException("Cancelled while creating item", e);
        } catch (ExecutionException e) {
            throw new RuntimeException("Execution interrupted while creating item", e);
        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted while creating item", e);
        }
    }

    public Item updateItem(Item item) throws Exception {
        try {
            firestore.collection(Item.PATH)
                    .document(item.getId())
                    .set(item).get();
            return getItem(item.getId());
        } catch (CancellationException e) {
            throw new RuntimeException("Cancelled while updating item", e);
        } catch (ExecutionException e) {
            throw new RuntimeException("Execution interrupted while updating item", e);
        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted while updating item", e);
        }
    }

    public void deleteItem(String itemId) throws Exception{
        firestore.collection("items").document(itemId).delete();
    }
}
