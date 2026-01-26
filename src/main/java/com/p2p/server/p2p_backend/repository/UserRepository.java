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

    // READ
    public void printUserById(String userId) throws Exception{

        DocumentSnapshot doc = firestore
                .collection("users")
                .document(userId)
                .get()
                .get();

        if (!doc.exists()) {
            System.out.println("User not found: " + userId);
            return;
        }

        User user = doc.toObject(User.class);

        // In case we lose it
        user.setId(userId);

        System.out.println("---- USER ----");
        System.out.println("Name: " + user.getFirstName() + " " + user.getLastName());

    }
}
