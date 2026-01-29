package com.p2p.server.p2p_backend.repository;
import com.p2p.server.p2p_backend.model.User;
import com.google.cloud.firestore.*;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    private final Firestore firestore;

    public UserRepository(Firestore firestore) {
        this.firestore = firestore;
    }

    public User getUser(String userId) throws Exception {
        try {
            DocumentSnapshot doc = firestore
                    .collection("users")
                    .document(userId)
                    .get()
                    .get();

            if (!doc.exists()) {
                System.out.println("User not found: " + userId);
                return null;
            }

            User user = doc.toObject(User.class);
            if (user == null) {
                throw new IllegalAccessException("Failed to map User: " + userId);
            }

            return user;
        } catch (Exception e) {
            throw new Exception("Something went wrong while fetching User " + userId, e);
        }
    }

    // DELETE
    public void deleteUser(String userId) throws Exception{
        try {
            firestore.collection("userId").document(userId).delete();
        } catch (Exception e) {
            throw new Exception("Failed to delete user with id: " + userId, e);
        }
    }
}
