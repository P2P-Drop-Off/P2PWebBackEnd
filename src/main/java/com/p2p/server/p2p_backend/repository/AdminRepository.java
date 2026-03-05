package com.p2p.server.p2p_backend.repository;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.p2p.server.p2p_backend.model.Admin;
import com.p2p.server.p2p_backend.model.User;

import java.util.concurrent.ExecutionException;

public class AdminRepository {

    private final Firestore firestore;

    public AdminRepository(Firestore firestore) {
        this.firestore = firestore;
    }

    public Admin getAdmin(String userId) throws Exception {
        try {
            DocumentSnapshot doc = firestore
                    .collection(User.PATH)
                    .document(userId)
                    .get().get();
            return doc.toObject(Admin.class);

        } catch (InterruptedException e) {
            //Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted while fetching user", e);
        } catch (ExecutionException e) {
            throw new RuntimeException("Failed to fetch user from database", e);
        }
    }
}
