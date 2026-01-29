package com.p2p.server.p2p_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import com.p2p.server.p2p_backend.repository.LocationRepository;
import com.p2p.server.p2p_backend.service.LocationService;
import com.p2p.server.p2p_backend.model.Location;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/locations")
public class LocationController {
    private final LocationRepository repository;
    private final LocationService service;

    public LocationController(LocationRepository repository, LocationService service) {
        this.repository = repository;
        this.service = service;
    }

    @GetMapping("/test")
    public ResponseEntity<Map<String, String>> test() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "ok");
        response.put("message", "Location endpoint is working");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Location> getLocation(@PathVariable String id) throws Exception {
        Location location = repository.getLocation(id);

        if (location == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(location);
    }

    @PostMapping("/post")
    public ResponseEntity<Map<String, String>> newLocation(@RequestBody Location location) {
        System.out.println("===== POST /locations/post called =====");
        System.out.println("Received location: " + location.getName());
        
        try {
            Location createdLocation = service.createLocation(location);
            
            System.out.println("Location created successfully with ID: " + createdLocation.getId());
            
            Map<String, String> response = new HashMap<>();
            response.put("id", createdLocation.getId());
            response.put("status", "success");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("ERROR creating location: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", e.getMessage());
            
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
}
