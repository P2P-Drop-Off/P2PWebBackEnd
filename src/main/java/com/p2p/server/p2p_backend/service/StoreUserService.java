package com.p2p.server.p2p_backend.service;

import com.p2p.server.p2p_backend.model.StoreUser;
import com.p2p.server.p2p_backend.repository.StoreUserRepository;
import org.springframework.stereotype.Service;
import java.time.Instant;

@Service
public class StoreUserService {

    private final StoreUserRepository repository;

    public StoreUserService(StoreUserRepository repository) {
        this.repository = repository;
    }

    public StoreUser createStoreUser(StoreUser storeUser) throws Exception {
        // Set UTC timestamp as ISO 8601 string for Firestore
        String utcTimestamp = Instant.now().toString();
        storeUser.setCreatedAt(utcTimestamp);
        return null;
        //return repository.createStoreUser(storeUser);
    }

    public StoreUser[] getNearbyStores(String userLocation) {
        // TODO: Filter by store location and availability
        return new StoreUser[0];
    }

    public void verifyDropOff(){
        // called when user drop off item. store must confirm it's arrived with a code from generateDropOffCode().
    }
}
