package com.p2p.server.p2p_backend.controller;

import com.p2p.server.p2p_backend.model.Location;
import com.p2p.server.p2p_backend.repository.LocationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.*;

@RestController
@RequestMapping("/locations")
public class LocationController {
    private final LocationRepository repository;

    public LocationController(LocationRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Location> getLocation(@PathVariable String id) throws Exception {
        Location location = repository.getLocation(id);

        if (location == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(location);
    }
}
