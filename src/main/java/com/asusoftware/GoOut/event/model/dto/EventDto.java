package com.asusoftware.GoOut.event.model.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class EventDto {
    private UUID id;
    private String title;
    private String description;
    private LocalDateTime datetime;
    private UUID locationId;
    private UUID createdByUserId;
    private Integer maxParticipants;
}
