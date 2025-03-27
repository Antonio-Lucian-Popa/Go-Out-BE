package com.asusoftware.GoOut.event.service;

import com.asusoftware.GoOut.event.model.Event;
import com.asusoftware.GoOut.event.model.dto.EventDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventService {
    EventDto create(EventDto event);
    Optional<EventDto> getById(UUID id);
    List<EventDto> getAll();
    List<EventDto> getByUserId(UUID userId);
    List<EventDto> getByLocationAndDate(UUID locationId, LocalDate date);
    long countParticipants(UUID eventId);
    void delete(UUID id);
}

