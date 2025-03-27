package com.asusoftware.GoOut.user.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@Table(name = "users")
@Entity(name = "User")
public class User {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;
    private String email;
    private String password;
    private String bio;

    @ElementCollection
    private List<String> interests;

    private String profilePic;

    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    public boolean hasRole(UserRole role) {
        return this.role == role;
    }
}
