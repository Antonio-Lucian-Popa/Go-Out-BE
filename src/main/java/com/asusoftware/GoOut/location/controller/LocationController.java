package com.asusoftware.GoOut.location.controller;

import com.asusoftware.GoOut.location.model.dto.LocationDto;
import com.asusoftware.GoOut.location.service.LocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/locations")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

   // Creează locație - doar ADMIN_LOCAL
    @PostMapping
    public ResponseEntity<LocationDto> create(@RequestBody LocationDto dto, @RequestHeader("X-USER-ID") UUID currentUserId) {
        return ResponseEntity.ok(locationService.create(dto, currentUserId));
    }

    // Toate locațiile
    @GetMapping
    public ResponseEntity<List<LocationDto>> getAll() {
        return ResponseEntity.ok(locationService.getAll());
    }

    // Locație după ID
    @GetMapping("/{id}")
    public ResponseEntity<LocationDto> getById(@PathVariable UUID id) {
        return locationService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Locațiile deținute de userul logat
    @GetMapping("/mine")
    public ResponseEntity<List<LocationDto>> getMyLocations(@RequestHeader("X-USER-ID") UUID currentUserId) {
        return ResponseEntity.ok(locationService.getLocationsByOwner(currentUserId));
    }

    @GetMapping("/bounds")
    public ResponseEntity<List<LocationDto>> getLocationsByBounds(
            @RequestParam double north,
            @RequestParam double south,
            @RequestParam double east,
            @RequestParam double west
    ) {
        List<LocationDto> locations = locationService.findWithinBounds(north, south, east, west);
        return ResponseEntity.ok(locations);
    }


    // Șterge locație dacă aparține userului
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id, @RequestHeader("X-USER-ID") UUID currentUserId) {
        locationService.delete(id, currentUserId);
        return ResponseEntity.noContent().build();
    }

    // Filtrare după tip
    @GetMapping("/type/{type}")
    public ResponseEntity<List<LocationDto>> getByType(@PathVariable String type) {
        return ResponseEntity.ok(locationService.findByType(type));
    }

    // Căutare locații apropiate (raza în km)
    @GetMapping("/nearby")
    public ResponseEntity<List<LocationDto>> getNearby(
            @RequestParam double lat,
            @RequestParam double lng,
            @RequestParam double radiusKm) {
        return ResponseEntity.ok(locationService.findNearby(lat, lng, radiusKm));
    }
}

