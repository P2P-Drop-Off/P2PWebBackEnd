package com.p2p.server.p2p_backend.repository;

import com.p2p.server.p2p_backend.model.StoreUser;
import com.google.cloud.firestore.*;
import org.springframework.stereotype.Repository;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

@Repository
public class StoreUserRepository {

    private final Firestore firestore;

    public StoreUserRepository(Firestore firestore) {
        this.firestore = firestore;
    }

    // READ
    public StoreUser getStoreUser(String userId) throws Exception {
        try {
            DocumentSnapshot doc = firestore
                    .collection("storeUsers")
                    .document(userId)
                    .get()
                    .get();

            if (!doc.exists()) {
                System.out.println("StoreUser not found: " + userId);
                return null;
            }

            StoreUser user = doc.toObject(StoreUser.class);
            if (user == null) {
                throw new IllegalAccessException("Failed to map StoreUser: " + userId);
            }

            return user;
        } catch (Exception e) {
            throw new Exception("Something went wrong while fetching StoreUser " + userId, e);
        }
    }

    public StoreUser createStoreUser(StoreUser storeUser) throws Exception {
        try {
            DocumentReference docRef = firestore.collection(StoreUser.PATH).document();
            storeUser.setId(docRef.getId());
            docRef.set(storeUser).get();
            return getStoreUser(storeUser.getId());
        } catch (CancellationException e) {
            throw new RuntimeException("Cancelled while creating store user", e);
        } catch (ExecutionException e) {
            throw new RuntimeException("Execution interrupted while creating store user", e);
        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted while creating store user", e);
        }
    }

    public StoreUser updateStoreUser(StoreUser storeUser) throws Exception {
        try {
            firestore.collection(StoreUser.PATH)
                    .document(storeUser.getId())
                    .set(storeUser).get();
            return getStoreUser(storeUser.getId());
        } catch (CancellationException e) {
            throw new RuntimeException("Cancelled while updating store user", e);
        } catch (ExecutionException e) {
            throw new RuntimeException("Execution interrupted while updating store user", e);
        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted while updating store user", e);
        }
    }

    public void deleteStoreUser(String storeUserId) throws Exception {
        firestore.collection(StoreUser.PATH).document(storeUserId).delete();
    }

}
