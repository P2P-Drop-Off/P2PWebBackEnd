package com.p2p.server.p2p_backend.repository;
import com.google.api.core.ApiFuture;
import com.p2p.server.p2p_backend.exceptions.ItemNotFoundException;
import com.p2p.server.p2p_backend.model.Item;
import com.p2p.server.p2p_backend.model.User;
import com.google.cloud.firestore.*;
import org.springframework.stereotype.Repository;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

@Repository
public class UserRepository {
    private final Firestore firestore;

    public UserRepository(Firestore firestore) {
        this.firestore = firestore;
    }

    public User getUser(String userId) throws Exception {
        try {
            DocumentSnapshot doc = firestore
                    .collection(User.PATH)
                    .document(userId)
                    .get().get();

            if (!doc.exists()) {
                throw new ItemNotFoundException(userId);
            }
            return doc.toObject(User.class);

        } catch (InterruptedException e) {
            //Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted while fetching user", e);
        } catch (ExecutionException e) {
            throw new RuntimeException("Failed to fetch user from database", e);
        }
    }

    public void deleteUser(String userId) throws Exception{
        firestore.collection(User.PATH).document(userId).delete();
    }

    public User createUser(User user) throws Exception {
        try {
            DocumentReference docRef = firestore.collection(User.PATH).document();
            user.setId(docRef.getId());
            docRef.set(user).get();
            return user;
        } catch (CancellationException e) {
            throw new RuntimeException("Cancelled while fetching user", e);
        } catch (ExecutionException e) {
            throw new RuntimeException("Execution interrupted while fetching user", e);
        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted while fetching user", e);
        }
    }

    public User updateUser(User user) throws Exception{
        try {
            WriteResult result = firestore
                    .collection(User.PATH)
                    .document(user.getId())
                    .set(user).get();
            return getUser(user.getId());
        } catch (CancellationException e) {
            throw new RuntimeException("Cancelled while fetching user", e);
        } catch (ExecutionException e) {
            throw new RuntimeException("Execution interrupted while fetching user", e);
        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted while fetching user", e);
        }
    }
}
