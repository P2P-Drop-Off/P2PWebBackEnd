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

    public Location getLocation(String locationId) throws Exception {
        try {
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
            if (location == null) {
                throw new IllegalAccessException("Failed to map Location: " + locationId);
            }

            return location;
        } catch (Exception e) {
            throw new Exception("Something went wrong while fetching Location " + locationId, e);
        }
    }

    // DELETE
    public void deleteLocation(String locationId) throws Exception{
        try {
            firestore.collection("locations").document(locationId).delete();
        } catch (Exception e) {
            throw new Exception("Failed to delete location with id: " + locationId, e);
        }
    }
}
