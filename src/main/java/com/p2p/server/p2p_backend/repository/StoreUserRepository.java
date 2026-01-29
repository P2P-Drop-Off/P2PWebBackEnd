package com.p2p.server.p2p_backend.repository;

import com.p2p.server.p2p_backend.model.StoreUser;
import com.google.cloud.firestore.*;
import com.p2p.server.p2p_backend.model.User;
import org.springframework.stereotype.Repository;

@Repository
public class StoreUserRepository {

    private final Firestore firestore;

    public StoreUserRepository(Firestore firestore) {
        this.firestore = firestore;
    }

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

    // DELETE
    public void deleteStoreUser(String storeUserId) throws Exception{
        try {
            firestore.collection("storeUsers").document(storeUserId).delete();
        } catch (Exception e) {
            throw new Exception("Failed to delete storeUser with id: " + storeUserId, e);
        }
    }
}
