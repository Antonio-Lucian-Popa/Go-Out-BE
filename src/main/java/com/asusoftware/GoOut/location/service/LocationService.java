package com.asusoftware.GoOut.location.service;

import com.asusoftware.GoOut.location.model.Location;
import com.asusoftware.GoOut.location.model.dto.LocationDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LocationService {
    LocationDto create(LocationDto locationDto, UUID currentUserId);
    Optional<LocationDto> getById(UUID id);
    List<LocationDto> getAll();
    void delete(UUID id, UUID currentUserId);
    List<LocationDto> findByType(String type);
    List<LocationDto> findNearby(double latitude, double longitude, double radiusKm);
    List<LocationDto> getLocationsByOwner(UUID ownerUserId);
}

