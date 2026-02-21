package com.p2p.server.p2p_backend.service;

import com.p2p.server.p2p_backend.model.Location;
import com.p2p.server.p2p_backend.repository.LocationRepository;
import org.springframework.stereotype.Service;
import java.time.Instant;

@Service
public class LocationService {

    private final LocationRepository repository;

    public LocationService(LocationRepository repository) {
        this.repository = repository;
    }

    public Location createLocation(Location location) throws Exception {
        // Set UTC timestamp as ISO 8601 string for Firestore
        String utcTimestamp = Instant.now().toString();
        location.setCreatedAt(utcTimestamp);
        
        return repository.createLocation(location);
    }

    public Location getLocation(String locationId) throws Exception {
        return repository.getLocation(locationId);
    }

    public Location updateLocation(Location location) throws Exception {
        return repository.updateLocation(location);
    }

    public void deleteLocation(String locationId) throws Exception {
        repository.deleteLocation(locationId);
    }
}
