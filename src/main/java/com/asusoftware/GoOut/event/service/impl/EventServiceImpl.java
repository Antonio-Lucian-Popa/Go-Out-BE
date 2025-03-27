package com.asusoftware.GoOut.event.service.impl;

import com.asusoftware.GoOut.event.model.Event;
import com.asusoftware.GoOut.event.model.dto.EventDto;
import com.asusoftware.GoOut.event.repository.EventParticipantRepository;
import com.asusoftware.GoOut.event.repository.EventRepository;
import com.asusoftware.GoOut.event.service.EventService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventParticipantRepository participantRepository;

    public EventServiceImpl(EventRepository eventRepository,
                            EventParticipantRepository participantRepository) {
        this.eventRepository = eventRepository;
        this.participantRepository = participantRepository;
    }

    @Override
    public EventDto create(EventDto dto) {
        if (dto.getDatetime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Data evenimentului trebuie să fie în viitor.");
        }
        Event event = toEntity(dto);
        return toDto(eventRepository.save(event));
    }

    @Override
    public Optional<EventDto> getById(UUID id) {
        return eventRepository.findById(id).map(this::toDto);
    }

    @Override
    public List<EventDto> getAll() {
        return eventRepository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public List<EventDto> getByUserId(UUID userId) {
        return eventRepository.findByCreatedByUserId(userId).stream().map(this::toDto).toList();
    }

    @Override
    public List<EventDto> getByLocationAndDate(UUID locationId, LocalDate date) {
        return eventRepository.findAll().stream()
                .filter(e -> e.getLocationId().equals(locationId))
                .filter(e -> e.getDatetime().toLocalDate().equals(date))
                .map(this::toDto)
                .toList();
    }

    @Override
    public long countParticipants(UUID eventId) {
        return participantRepository.findByEventId(eventId).size();
    }

    @Override
    public void delete(UUID id) {
        eventRepository.deleteById(id);
    }

    private Event toEntity(EventDto dto) {
        Event event = new Event();
        event.setTitle(dto.getTitle());
        event.setDescription(dto.getDescription());
        event.setDatetime(dto.getDatetime());
        event.setLocationId(dto.getLocationId());
        event.setCreatedByUserId(dto.getCreatedByUserId());
        event.setMaxParticipants(dto.getMaxParticipants());
        return event;
    }

    private EventDto toDto(Event event) {
        EventDto dto = new EventDto();
        dto.setId(event.getId());
        dto.setTitle(event.getTitle());
        dto.setDescription(event.getDescription());
        dto.setDatetime(event.getDatetime());
        dto.setLocationId(event.getLocationId());
        dto.setCreatedByUserId(event.getCreatedByUserId());
        dto.setMaxParticipants(event.getMaxParticipants());
        return dto;
    }
}

