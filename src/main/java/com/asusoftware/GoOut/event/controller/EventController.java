package com.asusoftware.GoOut.event.controller;

import com.asusoftware.GoOut.event.model.dto.EventDto;
import com.asusoftware.GoOut.event.service.EventService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<EventDto> create(@RequestBody EventDto dto) {
        return ResponseEntity.ok(eventService.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDto> get(@PathVariable UUID id) {
        return eventService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<EventDto>> getAll() {
        return ResponseEntity.ok(eventService.getAll());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<EventDto>> getByUser(@PathVariable UUID userId) {
        return ResponseEntity.ok(eventService.getByUserId(userId));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<EventDto>> getByLocationAndDate(
            @RequestParam UUID locationId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(eventService.getByLocationAndDate(locationId, date));
    }

    @GetMapping("/{id}/participants")
    public ResponseEntity<Long> countParticipants(@PathVariable UUID id) {
        return ResponseEntity.ok(eventService.countParticipants(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        eventService.delete(id);
        return ResponseEntity.noContent().build();
    }
}