package com.p2p.server.p2p_backend.repository;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.p2p.server.p2p_backend.model.Location;
import org.springframework.stereotype.Repository;

@Repository
public class LocationRepository {

    private final Firestore firestore;

    public LocationRepository(Firestore firestore) {
        this.firestore = firestore;
    }

    // READ
    public Location getLocation(String locationId) throws Exception {

        DocumentSnapshot doc = firestore
                .collection("locations")
                .document(locationId)
                .get()
                .get();

        if (!doc.exists()) {
            System.out.println("Location not found: " + locationId);
            return null;
        }

        Location location = doc.toObject(Location.class);

        System.out.println("---- Got Item ----");
        System.out.println("Name: " + location.getName() + " " + location.getAddress());

        return doc.toObject(Location.class);
    }
}
