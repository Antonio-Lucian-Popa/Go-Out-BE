package com.asusoftware.GoOut.location.model.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class LocationDto {
    private UUID id;
    private String name;
    private String address;
    private double latitude;
    private double longitude;
    private String type;
    private String imageUrl;
    private double rating;
    private int popularity;
    private String openingHours;
    private List<String> tags;
    private UUID ownerUserId;
}
