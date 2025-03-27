package com.asusoftware.GoOut.event.controller;

import com.asusoftware.GoOut.event.service.EventParticipantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/events")
public class EventParticipantController {

    private final EventParticipantService participantService;

    public EventParticipantController(EventParticipantService participantService) {
        this.participantService = participantService;
    }

    @PostMapping("/{eventId}/join")
    public ResponseEntity<Void> joinEvent(@PathVariable UUID eventId, @RequestHeader("X-USER-ID") UUID userId) {
        participantService.joinEvent(eventId, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{eventId}/leave")
    public ResponseEntity<Void> leaveEvent(@PathVariable UUID eventId, @RequestHeader("X-USER-ID") UUID userId) {
        participantService.leaveEvent(eventId, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/attending")
    public ResponseEntity<List<UUID>> getEventsForUser(@RequestHeader("X-USER-ID") UUID userId) {
        return ResponseEntity.ok(participantService.getUserEvents(userId));
    }

    @GetMapping("/{eventId}/participants/list")
    public ResponseEntity<List<UUID>> getParticipants(@PathVariable UUID eventId) {
        return ResponseEntity.ok(participantService.getEventParticipants(eventId));
    }
}