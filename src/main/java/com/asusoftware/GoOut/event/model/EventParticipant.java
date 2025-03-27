package com.asusoftware.GoOut.event.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@IdClass(EventParticipantId.class)
public class EventParticipant {

    @Id
    private UUID eventId;

    @Id
    private UUID userId;

    private LocalDateTime joinedAt;
}

