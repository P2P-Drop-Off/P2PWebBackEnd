package com.p2p.server.p2p_backend.repository;

import com.p2p.server.p2p_backend.exceptions.ItemNotFoundException;
import com.p2p.server.p2p_backend.model.Item;
import com.google.cloud.firestore.*;
import org.springframework.stereotype.Repository;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FirestoreItemRepository {

    private final Firestore firestore;

    public FirestoreItemRepository(Firestore firestore) {
        this.firestore = firestore;
    }

    public boolean sixDigitCodeExists(String code) throws Exception {
        CollectionReference items = firestore.collection("items");

        Query query = items.whereEqualTo("sixDigitCode", code);

        QuerySnapshot snapshot = query.get().get();

        return !snapshot.isEmpty();
    }

    public List<Item> getAllItems() throws Exception {

        List<Item> items = new ArrayList<>();

        ApiFuture<QuerySnapshot> future =
                firestore.collection("items").get();

        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        for (QueryDocumentSnapshot doc : documents) {
            Item item = doc.toObject(Item.class);
            item.setId(doc.getId());
            items.add(item);
        }

        return items;
    }

    public Item getItem(String itemId) throws Exception {
        try {
            DocumentSnapshot doc = firestore.collection(Item.PATH)
                                           .document(itemId)
                                           .get().get();
            if (!doc.exists()) {
                throw new ItemNotFoundException(itemId);
            }
            return doc.toObject(Item.class);
        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted while fetching item", e);
        } catch (ExecutionException e) {
            throw new RuntimeException("Failed to fetch item from Firestore", e);
        }
    }

    public Item createItem(Item item) throws Exception {
        try {
            System.out.println("Attempting to create item in Firestore: " + item.getTitle());

            DocumentReference docRef = firestore.collection(Item.PATH).document();
            item.setId(docRef.getId());

            // Log before saving
            System.out.println("Saving item to Firestore with ID: " + item.getId());
            docRef.set(item).get();

            // Log after successful save
            System.out.println("Item saved successfully: " + item.getId());
            return getItem(item.getId());

        } catch (Exception e) {
            System.err.println("Failed to create item in Firestore");
            e.printStackTrace();
            throw e;
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

    public void deleteItem(String itemId) throws Exception {
        firestore.collection("items")
                 .document(itemId)
                 .delete().get();
    }
}