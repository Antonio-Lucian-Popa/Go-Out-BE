package com.asusoftware.GoOut.event.service.impl;

import com.asusoftware.GoOut.event.model.EventParticipant;
import com.asusoftware.GoOut.event.model.EventParticipantId;
import com.asusoftware.GoOut.event.repository.EventParticipantRepository;
import com.asusoftware.GoOut.event.service.EventParticipantService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class EventParticipantServiceImpl implements EventParticipantService {

    private final EventParticipantRepository repo;

    public EventParticipantServiceImpl(EventParticipantRepository repo) {
        this.repo = repo;
    }

    @Override
    public void joinEvent(UUID eventId, UUID userId) {
        EventParticipant ep = new EventParticipant();
        ep.setEventId(eventId);
        ep.setUserId(userId);
        ep.setJoinedAt(LocalDateTime.now());
        repo.save(ep);
    }

    @Override
    public void leaveEvent(UUID eventId, UUID userId) {
        repo.deleteById(new EventParticipantId(eventId, userId));
    }

    @Override
    public List<UUID> getUserEvents(UUID userId) {
        return repo.findByUserId(userId)
                .stream()
                .map(EventParticipant::getEventId)
                .toList();
    }

    @Override
    public List<UUID> getEventParticipants(UUID eventId) {
        return repo.findByEventId(eventId)
                .stream()
                .map(EventParticipant::getUserId)
                .toList();
    }
}

