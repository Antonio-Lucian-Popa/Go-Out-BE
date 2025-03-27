package com.asusoftware.GoOut.location.service.impl;

import com.asusoftware.GoOut.location.model.Location;
import com.asusoftware.GoOut.location.model.dto.LocationDto;
import com.asusoftware.GoOut.location.repository.LocationRepository;
import com.asusoftware.GoOut.location.service.LocationService;
import com.asusoftware.GoOut.user.model.User;
import com.asusoftware.GoOut.user.model.UserRole;
import com.asusoftware.GoOut.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


import org.springframework.security.access.AccessDeniedException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class LocationServiceImpl implements LocationService {

    private final LocationRepository repo;
    private final UserRepository userRepo;

    public LocationServiceImpl(LocationRepository repo, UserRepository userRepo) {
        this.repo = repo;
        this.userRepo = userRepo;
    }

    @Override
    public LocationDto create(LocationDto dto, UUID currentUserId) {
        User user = userRepo.findById(currentUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.hasRole(UserRole.ADMIN_LOCAL)) {
            throw new AccessDeniedException("Doar un ADMIN_LOCAL poate crea locații.");
        }

        Location loc = toEntity(dto);
        loc.setOwnerUserId(currentUserId);
        return toDto(repo.save(loc));
    }

    @Override
    public Optional<LocationDto> getById(UUID id) {
        return repo.findById(id).map(this::toDto);
    }

    @Override
    public List<LocationDto> getAll() {
        return repo.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public void delete(UUID id, UUID currentUserId) {
        Location loc = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Locație inexistentă"));

        if (!loc.getOwnerUserId().equals(currentUserId)) {
            throw new AccessDeniedException("Nu poți șterge o locație care nu îți aparține.");
        }

        repo.deleteById(id);
    }

    @Override
    public List<LocationDto> findByType(String type) {
        return repo.findAll().stream()
                .filter(loc -> loc.getType().equalsIgnoreCase(type))
                .map(this::toDto)
                .toList();
    }

    @Override
    public List<LocationDto> findNearby(double lat, double lng, double radiusKm) {
        return repo.findAll().stream()
                .filter(loc -> haversine(loc.getLatitude(), loc.getLongitude(), lat, lng) <= radiusKm)
                .map(this::toDto)
                .toList();
    }

    @Override
    public List<LocationDto> getLocationsByOwner(UUID ownerUserId) {
        return repo.findAll().stream()
                .filter(loc -> ownerUserId.equals(loc.getOwnerUserId()))
                .map(this::toDto)
                .toList();
    }

    private Location toEntity(LocationDto dto) {
        Location loc = new Location();
        loc.setName(dto.getName());
        loc.setAddress(dto.getAddress());
        loc.setLatitude(dto.getLatitude());
        loc.setLongitude(dto.getLongitude());
        loc.setType(dto.getType());
        loc.setImageUrl(dto.getImageUrl());
        loc.setRating(dto.getRating());
        loc.setPopularity(dto.getPopularity());
        loc.setOpeningHours(dto.getOpeningHours());
        loc.setTags(dto.getTags());
        return loc;
    }

    private LocationDto toDto(Location loc) {
        LocationDto dto = new LocationDto();
        dto.setId(loc.getId());
        dto.setName(loc.getName());
        dto.setAddress(loc.getAddress());
        dto.setLatitude(loc.getLatitude());
        dto.setLongitude(loc.getLongitude());
        dto.setType(loc.getType());
        dto.setImageUrl(loc.getImageUrl());
        dto.setRating(loc.getRating());
        dto.setPopularity(loc.getPopularity());
        dto.setOpeningHours(loc.getOpeningHours());
        dto.setTags(loc.getTags());
        dto.setOwnerUserId(loc.getOwnerUserId());
        return dto;
    }

    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return R * c;
    }
}

