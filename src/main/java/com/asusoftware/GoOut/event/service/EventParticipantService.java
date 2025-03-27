package com.asusoftware.GoOut.event.service;

import java.util.List;
import java.util.UUID;

public interface EventParticipantService {
    void joinEvent(UUID eventId, UUID userId);
    void leaveEvent(UUID eventId, UUID userId);
    List<UUID> getUserEvents(UUID userId);
    List<UUID> getEventParticipants(UUID eventId);
}

