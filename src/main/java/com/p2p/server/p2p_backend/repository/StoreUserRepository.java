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

    // READ
    public StoreUser getStoreUser(String userId) throws Exception{

        DocumentSnapshot doc = firestore
                .collection("storeUsers")
                .document(userId)
                .get()
                .get();

        if (!doc.exists()) {
            System.out.println("StoreUser not found: " + userId);
            return null;
        }

        User user = doc.toObject(User.class);

        System.out.println("---- Got StoreUser ----");
        System.out.println("Name: " + user.getFirstName() + " " + user.getLastName());

        return doc.toObject(StoreUser.class);

    }
}
