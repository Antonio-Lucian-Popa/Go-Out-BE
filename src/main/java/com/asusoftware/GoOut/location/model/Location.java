package com.asusoftware.GoOut.location.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Table(name = "locations")
@Entity(name = "Location")
public class Location {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;
    private String address;
    private double latitude;
    private double longitude;
    private String type;
    private String imageUrl;

    private double rating;
    private int popularity; // număr de check-ins / participanți
    private String openingHours;
    @ElementCollection
    private List<String> tags;
    private UUID ownerUserId;
}
