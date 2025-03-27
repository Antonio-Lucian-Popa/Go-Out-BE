package com.asusoftware.GoOut.event.repository;

import com.asusoftware.GoOut.event.model.EventParticipant;
import com.asusoftware.GoOut.event.model.EventParticipantId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EventParticipantRepository extends JpaRepository<EventParticipant, EventParticipantId> {
    List<EventParticipant> findByEventId(UUID eventId);
    List<EventParticipant> findByUserId(UUID userId);
}

