package com.asusoftware.GoOut.event.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
public class EventParticipantId implements Serializable {
    private UUID eventId;
    private UUID userId;

    // equals() and hashCode() obligatorii
}
