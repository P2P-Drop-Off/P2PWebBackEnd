package com.p2p.server.p2p_backend.repository;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.p2p.server.p2p_backend.model.Location;
import org.springframework.stereotype.Repository;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

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

    public Location createLocation(Location location) throws Exception {
        try {
            DocumentReference docRef = firestore.collection(Location.PATH).document();
            location.setId(docRef.getId());
            docRef.set(location).get();
            return getLocation(location.getId());
        } catch (CancellationException e) {
            throw new RuntimeException("Cancelled while creating location", e);
        } catch (ExecutionException e) {
            throw new RuntimeException("Execution interrupted while creating location", e);
        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted while creating location", e);
        }
    }

    public Location updateLocation(Location location) throws Exception {
        try {
            firestore.collection(Location.PATH)
                    .document(location.getId())
                    .set(location).get();
            return getLocation(location.getId());
        } catch (CancellationException e) {
            throw new RuntimeException("Cancelled while updating location", e);
        } catch (ExecutionException e) {
            throw new RuntimeException("Execution interrupted while updating location", e);
        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted while updating location", e);
        }
    }

    public void deleteLocation(String locationId) throws Exception{
        try {
            firestore.collection("locations").document(locationId).delete();
        } catch (Exception e) {
            throw new Exception("Failed to delete location with id: " + locationId, e);
        }
    }
}
