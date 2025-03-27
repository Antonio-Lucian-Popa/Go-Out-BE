package com.asusoftware.GoOut.event.repository;

import com.asusoftware.GoOut.event.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {
    List<Event> findByLocationId(UUID locationId);
    List<Event> findByCreatedByUserId(UUID userId);
}

